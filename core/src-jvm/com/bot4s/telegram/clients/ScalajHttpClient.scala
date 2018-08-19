package com.bot4s.telegram.clients

import java.net.Proxy
import java.nio.file.Files

import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.methods.{Request, JsonRequest, MultipartRequest, Response}
import com.bot4s.telegram.models.InputFile
import com.bot4s.telegram.marshalling
import io.circe.parser.parse
import io.circe.{Decoder, Encoder}
import scalaj.http.{Http, MultiPart}
import slogging.StrictLogging

import scala.concurrent.{ExecutionContext, Future, blocking}

/** Scalaj-http Telegram Bot API client
  *
  * Provide transparent camelCase <-> underscore_case conversions during serialization/deserialization.
  *
  * The Scalaj-http supports the following InputFiles:
  *   InputFile.FileId
  *   InputFile.Contents
  *   InputFile.Path
  *
  * @param token Bot token
  */
class ScalajHttpClient(token: String, proxy: Proxy = Proxy.NO_PROXY, telegramHost: String = "api.telegram.org")
                      (implicit ec: ExecutionContext) extends RequestHandler with StrictLogging {

  val connectionTimeoutMs = 10000
  val readTimeoutMs = 50000

  private val apiBaseUrl = s"https://$telegramHost/bot$token/"

  def sendRequest[R, T <: Request[_]](request: T)(implicit encT: Encoder[T], decR: Decoder[R]): Future[R] = {
    val url = apiBaseUrl + request.methodName

    val scalajRequest = request match {
      case r: JsonRequest[_] =>
        Http(url)
          .postData(marshalling.toJson(request))
          .header("Content-Type", "application/json")

      case r: MultipartRequest[_] =>

        // InputFile.FileIds are encoded as query params.
        val (fileIds, files) = r.getFiles.partition {
          case (key, _: InputFile.FileId) => true
          case _ => false
        }

        val parts = files.map {
          case (camelKey, inputFile) =>
            val key = marshalling.snakenize(camelKey)
            inputFile match {
              case InputFile.FileId(id) =>
                throw new RuntimeException("InputFile.FileId cannot must be encoded as a query param")

              case InputFile.Contents(filename, contents) =>
                MultiPart(key, filename, "application/octet-stream", contents)

              case InputFile.Path(path) =>
                MultiPart(key, path.getFileName.toString(),
                  "application/octet-stream",
                  Files.newInputStream(path),
                  Files.size(path),
                  _ => ())

              case other =>
                throw new RuntimeException(s"InputFile $other not supported")
            }
        }

        val fields = parse(marshalling.toJson(request)).fold(throw _, _.asObject.map {
          _.toMap.mapValues {
            json =>
              json.asString.getOrElse(marshalling.printer.pretty(json))
          }
        })

        val fileIdsParams = fileIds.map {
          case (key, inputFile: InputFile.FileId) =>
            marshalling.snakenize(key) -> inputFile.fileId
        }

        val params = fields.getOrElse(Map())

        Http(url).params(params ++ fileIdsParams).postMulti(parts: _*)
    }

    import marshalling.responseDecoder

    Future {
      blocking {
        scalajRequest
          .timeout(connectionTimeoutMs, readTimeoutMs)
          .proxy(proxy)
          .asString
      }
    } map {
      x =>
        if (x.isSuccess)
          marshalling.fromJson[Response[R]](x.body)
        else
          throw new RuntimeException(s"Error ${x.code} on request")
    } map (processApiResponse[R])
  }

}