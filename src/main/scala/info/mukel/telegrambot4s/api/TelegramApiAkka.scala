package info.mukel.telegrambot4s.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling._
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import info.mukel.telegrambot4s.api.Marshalling._
import info.mukel.telegrambot4s.methods.{ApiRequest, ApiRequestJson, ApiRequestMultipart, ApiResponse}

import scala.concurrent.Future

/** Akka-backed Telegram Bot API client
  * Provide transparent camelCase <-> underscore_case conversions during serialization/deserialization
  *
  * @param token Bot token
  */
class TelegramApiAkka(token: String)(implicit system: ActorSystem, materializer: Materializer) extends RequestHandler {

  import system.dispatcher

  private val apiBaseUrl = s"https://api.telegram.org/bot$token/"

  /** Extract request URL from class name.
    */
  private def getRequestUrl[R](r: ApiRequest[R]): String = apiBaseUrl + r.getClass.getSimpleName.reverse.dropWhile(_ == '$').reverse

  private val http = Http()

  def toHttpRequest[R: Manifest](r: ApiRequest[R]): Future[HttpRequest] = {
    val requestEntity = r match {
      case multipartRequest: ApiRequestMultipart[R] =>
        Marshal(multipartRequest).to[RequestEntity]

      case jsonRequest: ApiRequestJson[R] =>
        Marshal(jsonRequest).to[RequestEntity]
    }

    requestEntity map {
      re =>
        HttpRequest(HttpMethods.POST, Uri(getRequestUrl(r)), entity = re)
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
        case ApiResponse(true, Some(result), _, _) =>
          Future.successful(result)

        case ApiResponse(false, _, description, Some(errorCode)) =>
          Future.failed(
            TelegramApiException(description.getOrElse("Unexpected/invalid/empty response"), errorCode)
          )

        // case _ => // Probably a de/serialization error, just raise MatchError
      }
  }
}
