package info.mukel.telegram.bots.v2.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling._
import akka.http.scaladsl.model.{HttpEntity, _}
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshal, Unmarshaller}
import akka.stream.Materializer
import info.mukel.telegram.bots.v2.methods._
import info.mukel.telegram.bots.v2.model.InputFile
import org.json4s._
import org.json4s.ext.EnumNameSerializer
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization

//import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TelegramApiAkka(token: String)(implicit system: ActorSystem, materializer: Materializer) extends Jsonification {

  import system.dispatcher

  private val apiBaseUrl = s"https://api.telegram.org/bot$token/"

  /** Extract request URL from class name.
    */
  private def getRequestUrl[R](r: ApiRequest[R]): String = apiBaseUrl + r.getClass.getSimpleName.reverse.dropWhile(_ == '$').reverse

  /**
    * Transparent camelCase <-> underscore_case conversions during serialization/deserialization
    */

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

  /**
    * Spawns a type-safe request.
    *
    * @param request
    * @tparam R The request's expected result type
    * @return The request result wrapped in a Future (async)
    */
  def request[R: Manifest](request: ApiRequest[R]): Future[R] = {
    toHttpRequest(request)
      .flatMap(http.singleRequest(_))
      .flatMap(toApiResponse[R])
      .flatMap {
        case ApiResponse(true, Some(result), _, _) =>
          Future.successful(result)

        case ApiResponse(false, _, description, errorCode) =>
          Future.failed(
            new TelegramApiException(description.getOrElse("Invalid/empty response"))
          )

        // case _ => // Probably a de/serialization error, just raise MatchError
      }
  }
}
