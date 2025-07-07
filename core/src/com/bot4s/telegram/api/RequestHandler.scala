package com.bot4s.telegram.api

import java.util.UUID

import cats.MonadError
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.monadError._
import com.bot4s.telegram.methods._
import io.circe.{ Decoder, Encoder }
import com.typesafe.scalalogging.StrictLogging
import io.circe.Decoder._

import com.bot4s.telegram.marshalling._

abstract class RequestHandler[F[_]](implicit monadError: MonadError[F, Throwable]) extends StrictLogging {

  def sendRequest[T <: Request: Encoder](request: T)(implicit d: Decoder[request.Response]): F[request.Response]

  /**
   * Spawns a type-safe request.
   *
   * @param request
   * @tparam R Request's expected result type
   * @return The request result wrapped in a Future (async)
   */
  def apply[T <: Request: Encoder](request: T)(implicit d: Decoder[request.Response]): F[request.Response] =
    for {
      uuid <- monadError.pure {
                val uuid = UUID.randomUUID()
                logger.trace("REQUEST {} {}", uuid, request)
                uuid
              }
      result <- monadError
                  .attempt(sendRequest(request))
                  .flatTap {
                    case Right(response) =>
                      monadError.pure(logger.trace("RESPONSE {} {}", uuid, response))
                    case Left(e) =>
                      monadError.pure(logger.error("RESPONSE {} {}", uuid, e))
                  }
                  .rethrow
    } yield result

  protected def processApiResponse[R](response: Response[R]): R = response match {
    case Response(true, Some(result), _, _, _) => result
    case Response(false, _, description, Some(errorCode), parameters) =>
      throw TelegramApiException(
        description.getOrElse("Unexpected/invalid/empty response"),
        errorCode,
        None,
        parameters
      )

    case other =>
      throw new RuntimeException(s"Unexpected API response: $other")
  }
}
