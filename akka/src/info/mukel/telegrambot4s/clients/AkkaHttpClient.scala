package info.mukel.telegrambot4s.clients

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling._
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import info.mukel.telegrambot4s.api.RequestHandler
import info.mukel.telegrambot4s.marshalling.AkkaHttpMarshalling
import info.mukel.telegrambot4s.methods.{ApiRequest, ApiResponse}
import io.circe.{Decoder, Encoder}
import slogging.StrictLogging

import scala.concurrent.{ExecutionContext, Future}

/** Akka-backed Telegram Bot API client
  * Provide transparent camelCase <-> underscore_case conversions during serialization/deserialization
  *
  * @param token Bot token
  */
class AkkaHttpClient(token: String, telegramHost: String = "api.telegram.org")(implicit system: ActorSystem, materializer: Materializer, ec: ExecutionContext) extends RequestHandler with StrictLogging {
  import AkkaHttpMarshalling._
  import info.mukel.telegrambot4s.marshalling.CirceMarshaller._
  private val apiBaseUrl = s"https://$telegramHost/bot$token/"
  private val http = Http()
  override def sendRequest[R, T <: ApiRequest[_]](request: T)(implicit encT: Encoder[T], decR: Decoder[R]): Future[R] = {
    Marshal(request).to[RequestEntity]
      .map {
        re =>
          HttpRequest(HttpMethods.POST, Uri(apiBaseUrl + request.methodName), entity = re)
      }
      .flatMap(http.singleRequest(_))
      .flatMap(r => Unmarshal(r.entity).to[ApiResponse[R]])
      .map(processApiResponse[R])
  }
}
