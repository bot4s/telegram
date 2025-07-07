package com.bot4s.telegram.clients

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.Uri.Path
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import akka.stream.scaladsl.{ Sink, Source }
import cats.instances.future._
import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.methods.{ Request, Response }
import io.circe.{ Decoder, Encoder }
import com.typesafe.scalalogging.StrictLogging
import com.bot4s.telegram.marshalling.responseDecoder

import scala.concurrent.{ ExecutionContext, Future }

class YetAnotherAkkaClient(token: String, telegramHost: String = "api.telegram.org")(implicit
  system: ActorSystem,
  materializer: Materializer,
  ec: ExecutionContext
) extends RequestHandler[Future]
    with StrictLogging {

  private val flow = Http().outgoingConnectionHttps(telegramHost)

  import com.bot4s.telegram.marshalling.AkkaHttpMarshalling._

  override def sendRequest[T <: Request: Encoder](
    request: T
  )(implicit d: Decoder[request.Response]): Future[request.Response] =
    Source
      .future(Marshal(request).to[RequestEntity].map { re =>
        HttpRequest(HttpMethods.POST, Uri(path = Path(s"/bot$token/" + request.methodName)), entity = re)
      })
      .via(flow)
      .mapAsync(1)(r => Unmarshal(r.entity).to[Response[request.Response]])
      .runWith(Sink.head)
      .map(v => processApiResponse[request.Response](v))
}
