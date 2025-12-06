package com.bot4s.telegram.clients

import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.http.scaladsl.marshalling._
import org.apache.pekko.http.scaladsl.model._
import org.apache.pekko.http.scaladsl.unmarshalling.Unmarshal
import org.apache.pekko.stream.Materializer
import cats.instances.future._
import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.marshalling.PekkoHttpMarshalling
import com.bot4s.telegram.marshalling._
import com.bot4s.telegram.methods.{ Request, Response }
import io.circe.{ Decoder, Encoder }
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.{ ExecutionContext, Future }

/**
 * Pekko-backed Telegram Bot API client
 * Provide transparent camelCase <-> underscore_case conversions during serialization/deserialization
 *
 * @param token Bot token
 */
class PekkoHttpClient(token: String, telegramHost: String = "api.telegram.org")(implicit
  system: ActorSystem,
  materializer: Materializer,
  ec: ExecutionContext
) extends RequestHandler[Future]
    with StrictLogging {

  import PekkoHttpMarshalling._
  private val apiBaseUrl = s"https://$telegramHost/bot$token/"
  private val http       = Http()

  override def sendRequest[T <: Request: Encoder](
    request: T
  )(implicit d: Decoder[request.Response]): Future[request.Response] =
    Marshal(request)
      .to[RequestEntity]
      .map { re =>
        HttpRequest(HttpMethods.POST, Uri(apiBaseUrl + request.methodName), entity = re)
      }
      .flatMap(http.singleRequest(_))
      .flatMap(r => Unmarshal(r.entity).to[Response[request.Response]])
      .map(t => processApiResponse[request.Response](t))
}
