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
import sttp.client4._

import sttp.client4.ResponseAs
import sttp.model.MediaType
import sttp.client4.StringBody
import sttp.client4.{ Request => SttpRequest }

/**
 * Sttp HTTP client.
 * Supports browsers via sttp's FetchBackend.
 *
 * @param token Bot token
 */
class SttpClient[F[_]](token: String, telegramHost: String = "api.telegram.org")(implicit
  backend: Backend[F],
  monadError: MonadError[F, Throwable]
) extends RequestHandler[F]()(using monadError)
    with StrictLogging {

  val readTimeout: Duration = 50.seconds

  private def asJson[B: Encoder](b: B): StringBody =
    StringBody(marshalling.toJson[B](b), "utf-8", MediaType.ApplicationJson)

  private def asJson[B: Decoder]: ResponseAs[B] =
    asStringAlways("utf-8").map(s => marshalling.fromJson[B](s))

  private val apiBaseUrl = s"https://$telegramHost/bot$token/"

  override def sendRequest[T <: BotRequest: Encoder](
    request: T
  )(implicit d: Decoder[request.Response]): F[request.Response] = {
    val url = apiBaseUrl + request.methodName

    val sttpRequest: Either[IllegalArgumentException, SttpRequest[String]] = request match {
      case r: JsonRequest =>
        Right(quickRequest.post(uri"$url").body(asJson(request)))

      case r: MultipartRequest =>
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

    monadError.fromEither(sttpRequest).flatMap { sttpRequest =>
      logger.debug(sttpRequest.toCurl)

      import com.bot4s.telegram.marshalling.responseDecoder

      val response = sttpRequest
        .readTimeout(readTimeout)
        .response(asJson[Response[request.Response]])
        .send(backend)

      response
        .map(_.body)
        .map(v => processApiResponse[request.Response](v))
    }

  }
}
