package com.bot4s.telegram.clients

import cats.instances.future._
import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.marshalling
import com.bot4s.telegram.methods.{JsonRequest, MultipartRequest, Request, Response}
import com.bot4s.telegram.models.InputFile
import com.google.common.base.Charsets
import com.google.common.io.BaseEncoding
import com.typesafe.scalalogging.StrictLogging
import io.circe.parser.parse
import io.circe.{Decoder, Encoder}
import ru.paperbird.botplatform.back.platform.telegram.ScalajHttpClient.ProxyCredentials
import scalaj.http.{Http, HttpRequest, MultiPart}

import java.net.Proxy
import java.nio.file.Files
import scala.concurrent.{ExecutionContext, Future, blocking}

/**
 * Scalaj-http Telegram Bot API client
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
class ScalajHttpClient(
                        token: String,
                        proxy: Proxy = Proxy.NO_PROXY,
                        proxyCredentials: Option[ProxyCredentials] = None,
                        telegramHost: String = "api.telegram.org"
                      )(implicit
                        ec: ExecutionContext
                      ) extends RequestHandler[Future]
  with StrictLogging {

  val connectionTimeoutMs = 1000
  val readTimeoutMs = 5000

  private val apiBaseUrl = s"https://$telegramHost/bot$token/"

  def sendRequest[R, T <: Request[_]](request: T)(implicit encT: Encoder[T], decR: Decoder[R]): Future[R] = {
    val url = apiBaseUrl + request.methodName

    val scalajRequest: HttpRequest = buildHttpRequest(request, url)

    import marshalling.responseDecoder

    val httpRequestWithTimeout = scalajRequest.timeout(connectionTimeoutMs, readTimeoutMs)
    val httpRequestWithProxy = proxyCredentials
      .map { cr =>
        val basicAuth = s"${cr.login}:${cr.password}"
        val basicAuthBase64 = BaseEncoding.base64().encode(basicAuth.getBytes(Charsets.UTF_8))
        httpRequestWithTimeout.header("Proxy-Authorization", s"Basic $basicAuthBase64")
      }
      .getOrElse(httpRequestWithTimeout)
      .proxy(proxy)

    Future(blocking(httpRequestWithProxy.asString)) map { x =>
      if (x.isSuccess)
        marshalling.fromJson[Response[R]](x.body)
      else
        throw new RuntimeException(s"Error ${x.code} on request")
    } map (processApiResponse[R])
  }

  private def buildHttpRequest[T <: Request[_], R](request: T, url: String)(implicit
                                                                            encT: Encoder[T]
  ) = request match {
    case _: JsonRequest[_] =>
      Http(url)
        .postData(marshalling.toJson(request))
        .header("Content-Type", "application/json")

    case r: MultipartRequest[_] =>
      // InputFile.FileIds are encoded as query params.
      val (fileIds, files) = r.getFiles.partition {
        case (key, _: InputFile.FileId) => true
        case _                          => false
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
              MultiPart(
                key,
                path.getFileName.toString,
                "application/octet-stream",
                Files.newInputStream(path),
                Files.size(path),
                _ => ()
              )

            case other =>
              throw new RuntimeException(s"InputFile $other not supported")
          }
      }

      val fields = parse(marshalling.toJson(request)).fold(
        throw _,
        _.asObject.map {
          _.toMap.mapValues { json =>
            json.asString.getOrElse(json.printWith(marshalling.printer))
          }
        }
      )

      val fileIdsParams = fileIds.map {
        case (key, inputFile: InputFile.FileId) =>
          marshalling.snakenize(key) -> inputFile.fileId
      }

      val params = fields.getOrElse(Map())

      Http(url).params((params ++ fileIdsParams).toSeq).postMulti(parts: _*)
  }

}

object ScalajHttpClient {
  case class ProxyCredentials(login: String, password: String)

}
