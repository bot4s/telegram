package com.bot4s.telegram.clients

import cats.MonadError
import cats.syntax.functor._
import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.marshalling.CaseConversions
import com.bot4s.telegram.methods.{JsonRequest, MultipartRequest, Response}
import com.softwaremill.sttp.{Request => _, Response => _, _}
import com.bot4s.telegram.marshalling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models.InputFile
import io.circe.parser.parse
import io.circe.{Decoder, Encoder}
import com.typesafe.scalalogging.StrictLogging

import scala.util.Try
import scala.concurrent.duration._

/** Sttp HTTP client.
  * Supports browsers via sttp's FetchBackend.
  *
  * @param token Bot token
  */
class SttpClient[F[_]](token: String, telegramHost: String = "api.telegram.org")
                      (implicit backend: SttpBackend[F, Nothing], monadError: MonadError[F, Throwable])
  extends RequestHandler[F]()(monadError) with StrictLogging {

  val readTimeout: Duration = 50.seconds

  private implicit def circeBodySerializer[B : Encoder]: BodySerializer[B] =
    b => StringBody(marshalling.toJson[B](b), "utf-8", Some(MediaTypes.Json))

  private def asJson[B : Decoder]: ResponseAs[B, Nothing] =
    asString("utf-8").map(s => marshalling.fromJson[B](s))

  private val apiBaseUrl = s"https://$telegramHost/bot$token/"

  override def sendRequest[R, T <: Request[_]](request: T)(implicit encT: Encoder[T], decR: Decoder[R]): F[R] = {
    val url = apiBaseUrl + request.methodName

    val sttpRequest: RequestT[Id, String, Nothing] = request match {
      case r: JsonRequest[_] =>
        sttp.post(uri"$url").body(request)

      case r: MultipartRequest[_] =>
        val files = r.getFiles

        val parts = files.map {
          case (camelKey, inputFile) =>
            val key = CaseConversions.snakenize(camelKey)
            inputFile match {
              case InputFile.FileId(id) => multipart(key, id)
              case InputFile.Contents(filename, contents) => multipart(key, contents).fileName(filename)
              //case InputFile.Path(path) => multipartFile(key, path)
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

        val params = fields.getOrElse(Map())

        sttp.post(uri"$url?$params").multipartBody(parts)
    }

    import com.bot4s.telegram.marshalling.responseDecoder

    val response = sttpRequest
      .readTimeout(readTimeout)
      .parseResponseIf(_ => true) // Always parse response
      .response(asJson[Response[R]])
      .send[F]()

    response
      .map(_.unsafeBody)
      .flatMap(t => monadError.fromTry(Try(processApiResponse[R](t))))
  }
}
