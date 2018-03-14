package info.mukel.telegrambot4s.backends.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling._
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import com.typesafe.scalalogging.StrictLogging
import info.mukel.telegrambot4s.api.TelegramApiException
import info.mukel.telegrambot4s.api.{RequestHandler, TelegramApiException}
import info.mukel.telegrambot4s.marshalling.AkkaHttpMarshalling
import info.mukel.telegrambot4s.methods.{ApiRequest, ApiResponse}

import scala.concurrent.{ExecutionContext, Future}

/** Akka-backed Telegram Bot API client
  * Provide transparent camelCase <-> underscore_case conversions during serialization/deserialization
  *
  * @param token Bot token
  */
class AkkaClient(token: String, telegramHost: String = "api.telegram.org")(implicit system: ActorSystem, materializer: Materializer, ec: ExecutionContext) extends RequestHandler with StrictLogging {

  import AkkaHttpMarshalling._

  private val apiBaseUrl = s"https://$telegramHost/bot$token/"

  private val http = Http()

  private def toHttpRequest[R: Manifest](r: ApiRequest[R]): Future[HttpRequest] = {
    Marshal(r).to[RequestEntity]
      .map {
        re =>
          HttpRequest(HttpMethods.POST, Uri(apiBaseUrl + r.methodName), entity = re)
      }
  }

  private def toApiResponse[R: Manifest](httpResponse: HttpResponse): Future[ApiResponse[R]] = {
    Unmarshal(httpResponse.entity).to[ApiResponse[R]]
  }

  /** Spawns a type-safe request.
    *
    * @param request
    * @tparam R Request's expected result type
    * @return The request result wrapped in a Future (async)
    */
  override def apply[R: Manifest](request: ApiRequest[R]): Future[R] = {
    toHttpRequest(request)
      .flatMap(http.singleRequest(_))
      .flatMap(toApiResponse[R])
      .flatMap {
        case ApiResponse(true, Some(result), _, _, _) =>
          Future.successful(result)

        case ApiResponse(false, _, description, Some(errorCode), parameters) =>
          val e = TelegramApiException(description.getOrElse("Unexpected/invalid/empty response"), errorCode, None, parameters)
          logger.error("Telegram API exception", e)
          Future.failed(e)

        case _ =>
          val msg = "Error on request response"
          logger.error(msg)
          Future.failed(new Exception(msg))
      }
  }
}
