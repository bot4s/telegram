package info.mukel.telegrambot4s.clients

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.Uri.Path
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source}
import info.mukel.telegrambot4s.api.RequestHandler
import info.mukel.telegrambot4s.methods.{ApiRequest, ApiResponse}
import io.circe.{Decoder, Encoder}
import slogging.StrictLogging

import scala.concurrent.{ExecutionContext, Future}

class YetAnotherAkkaClient(token: String, telegramHost: String = "api.telegram.org")
                          (implicit system: ActorSystem, materializer: Materializer, ec: ExecutionContext)
  extends RequestHandler with StrictLogging {

  private val flow = Http().outgoingConnectionHttps(telegramHost)

  import info.mukel.telegrambot4s.marshalling.AkkaHttpMarshalling._
  import info.mukel.telegrambot4s.marshalling.CirceMarshaller._

  override def sendRequest[R, T <: ApiRequest[_]](request: T)(implicit encT: Encoder[T], decR: Decoder[R]): Future[R] = {
    Source.fromFuture(
      Marshal(request).to[RequestEntity]
        .map {
          re =>
            HttpRequest(HttpMethods.POST, Uri(path = Path(s"/bot$token/" + request.methodName)), entity = re)
        })
      .via(flow)
      .mapAsync(1)(r => Unmarshal(r.entity).to[ApiResponse[R]])
      .runWith(Sink.head)
      .map(processApiResponse[R])
  }
}