package com.bot4s.telegram.clients

import cats._
import cats.syntax.functor._
import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.log.Logger
import com.bot4s.telegram.marshalling.CaseConversions
import com.bot4s.telegram.methods.{JsonRequest, MultipartRequest, Response}
import sttp.client.{Request => _, Response => _, _}
import com.bot4s.telegram.marshalling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models.InputFile
import io.circe.parser.parse
import io.circe.{Decoder, Encoder}
import com.bot4s.telegram.marshalling.responseDecoder

import scala.concurrent.duration._

/** Sttp HTTP client.
  * Supports browsers via sttp's FetchBackend.
  *
  * @param token Bot token
  */
class SttpClient[F[_]](token: String,
                       telegramHost: String = "api.telegram.org",
                       val logger: Logger[F])(
  implicit backend: SttpBackend[F, Nothing, Any],
  monadError: MonadError[F, Throwable],
) extends RequestHandler[F]()(monadError) {

  val readTimeout: Duration = 50.seconds

  private implicit def circeBodySerializer[B: Encoder]: BodySerializer[B] =
    b =>
      StringBody(
        marshalling.toJson[B](b),
        "utf-8",
        Some(sttp.model.MediaType.ApplicationJson)
    )

  private def asJson[B: Decoder]: ResponseAs[B, Nothing] =
    asStringAlways("utf-8").map(s => marshalling.fromJson[B](s))

  private val apiBaseUrl = s"https://$telegramHost/bot$token/"

  override def sendRequest[R, T <: Request[_]](
    request: T
  )(implicit encT: Encoder[T], decR: Decoder[R]): F[R] = {
    val url = apiBaseUrl + request.methodName

    val sttpRequest: RequestT[Id, String, Nothing] = request match {
      case r: JsonRequest[_] => quickRequest.post(uri"$url").body(request)

      case r: MultipartRequest[_] =>
        val files = r.getFiles

        val parts = files.map {
          case (camelKey, inputFile) =>
            val key = CaseConversions.snakenize(camelKey)
            inputFile match {
              case InputFile.FileId(id) => multipart(key, id)
              case InputFile.Contents(filename, contents) =>
                multipart(key, contents).fileName(filename)
              //case InputFile.Path(path) => multipartFile(key, path)
              case other =>
                throw new RuntimeException(s"InputFile $other not supported")
            }
        }

        val fields =
          parse(marshalling.toJson(request)).fold(throw _, _.asObject.map {
            _.toMap.mapValues { json =>
              json.asString.getOrElse(marshalling.printer.pretty(json))
            }
          })

        val params = fields.getOrElse(Map())
        quickRequest.post(uri"$url?$params").multipartBody(parts)
    }

    val response = sttpRequest
      .readTimeout(readTimeout)
      .response(asJson[Response[R]])
      .send()

    response
      .map(_.body)
      .map(processApiResponse[R])
  }
}
