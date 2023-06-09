package com.bot4s.telegram.clients

import cats.MonadError
import cats.syntax.functor._
import cats.syntax.flatMap._
import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.marshalling.CaseConversions
import com.bot4s.telegram.methods.{ JsonRequest, MultipartRequest, Request => BotRequest, Response }
import com.bot4s.telegram.marshalling
import com.bot4s.telegram.models.InputFile
import io.circe.parser.parse
import io.circe.{ Decoder, Encoder }
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.duration._
import sttp.client3._

import sttp.client3.ResponseAs
import sttp.model.MediaType
import sttp.client3.BodySerializer
import sttp.client3.StringBody
import sttp.client3.{ Request => SttpRequest }

/**
 * Sttp HTTP client.
 * Supports browsers via sttp's FetchBackend.
 *
 * @param token Bot token
 */
class SttpClient[F[_]](token: String, telegramHost: String = "api.telegram.org")(implicit
  backend: SttpBackend[F, Any],
  monadError: MonadError[F, Throwable]
) extends RequestHandler[F]()(monadError)
    with StrictLogging {

  val readTimeout: Duration = 50.seconds

  private implicit def circeBodySerializer[B: Encoder]: BodySerializer[B] =
    b => StringBody(marshalling.toJson[B](b), "utf-8", MediaType.ApplicationJson)

  private def asJson[B: Decoder]: ResponseAs[B, Any] =
    asStringAlways("utf-8").map(s => marshalling.fromJson[B](s))

  private val apiBaseUrl = s"https://$telegramHost/bot$token/"

  override def sendRequest[R, T <: BotRequest[_]](request: T)(implicit encT: Encoder[T], decR: Decoder[R]): F[R] = {
    val url = apiBaseUrl + request.methodName

    val sttpRequest: Either[IllegalArgumentException, SttpRequest[String, Any]] = request match {
      case r: JsonRequest[_] =>
        Right(quickRequest.post(uri"$url").body(request))

      case r: MultipartRequest[_] =>
        val parts = r.getFiles.flatMap { case (camelKey, inputFile) =>
          val key = CaseConversions.snakenize(camelKey)
          inputFile match {
            // FileId must be submitted through the JSON query, see `inputFileEncoder`
            case InputFile.FileId(id)                   => None
            case InputFile.Contents(filename, contents) => Some(multipart(key, contents).fileName(filename))
            case InputFile.Path(path)                   => Some(multipartFile(key, path))
            case other                                  => throw new RuntimeException(s"InputFile $other not supported")
          }
        }

        val fields = parse(marshalling.toJson(request))
          .fold(
            throw _,
            _.asObject.map {
              _.toMap.mapValues { json =>
                json.asString.getOrElse(json.printWith(marshalling.printer))
              }.toMap.map { case (name, value) => multipart(name, value) }
            }
          )

        val params = fields.getOrElse(List.empty).toList

        Right(quickRequest.post(uri"$url").multipartBody(params ++ parts))
      case req =>
        Left(new IllegalArgumentException(f"Unsupported request type ${req.getClass().getName()}"))
    }

    monadError.fromEither(sttpRequest).flatMap { request =>
      logger.debug(request.toCurl)

      import com.bot4s.telegram.marshalling.responseDecoder

      val response = request
        .readTimeout(readTimeout)
        .response(asJson[Response[R]])
        .send(backend)

      response
        .map(_.body)
        .map(processApiResponse[R])
    }

  }
}
