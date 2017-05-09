package info.mukel.telegrambot4s.cllients

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.Uri.Path
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.QueueOfferResult.Enqueued
import akka.stream.scaladsl.{Keep, Sink, Source}
import akka.stream.{Materializer, OverflowStrategy}
import com.typesafe.scalalogging.StrictLogging
import info.mukel.telegrambot4s.api.{RequestHandler, TelegramApiException}
import info.mukel.telegrambot4s.marshalling.HttpMarshalling
import info.mukel.telegrambot4s.methods.{ApiRequest, ApiResponse}

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.{Failure, Success, Try}

class SourceQueueClient(token: String, telegramHost: String = "api.telegram.org", queueSize: Int = 1024)
                      (implicit system: ActorSystem, materializer: Materializer, ec: ExecutionContext)
  extends RequestHandler with StrictLogging {

  import HttpMarshalling._

  val availableProcessors = Runtime.getRuntime().availableProcessors()

  private lazy val pool = Http().cachedHostConnectionPoolHttps[Promise[HttpResponse]](host = telegramHost)

  private lazy val queue = Source.queue[(ApiRequest[_], Promise[HttpResponse])](queueSize, OverflowStrategy.dropNew)
    .mapAsync(availableProcessors){ case (r, p) => toHttpRequest(r) map { (_ -> p)} }
    .via(pool)
    .toMat(Sink.foreach[(Try[HttpResponse], Promise[HttpResponse])]({
      case ((Success(resp), p)) => p.success(resp)
      case ((Failure(e), p)) => p.failure(e)
    }))(Keep.left)
    .run()

  /** Spawns a type-safe request.
    *
    * @param request
    * @tparam R Request's expected result type
    * @return The request result wrapped in a Future (async)
    */
  override def apply[R: Manifest](request: ApiRequest[R]): Future[R] = {
    val promise = Promise[HttpResponse]

    val response = queue.synchronized {
      queue.offer((request, promise)).flatMap {
        case Enqueued => promise.future.flatMap(r => toApiResponse[R](r))
        case _ => Future.failed(new RuntimeException("Failed to send request, pending queue is full."))
      }
    }

    response flatMap {
      case ApiResponse(true, Some(result), _, _, _) =>
        Future.successful(result)

      case ApiResponse(false, _, description, Some(errorCode), parameters) =>
        val e = TelegramApiException(description.getOrElse("Unexpected/invalid/empty response"), errorCode, None, parameters)
        logger.error("Telegram API exception", e)
        Future.failed(e)

      case _ =>
        val msg = "Error on request response"
        logger.error(msg)
        Future.failed(new RuntimeException(msg))
    }
  }

  private def toHttpRequest[R](r: ApiRequest[R]): Future[HttpRequest] = {
    Marshal(r).to[RequestEntity]
      .map {
        re =>
          HttpRequest(HttpMethods.POST,
            Uri(path = Path(s"/bot$token/" + r.methodName)),
            entity = re)
      }
  }

  private def toApiResponse[R: Manifest](httpResponse: HttpResponse): Future[ApiResponse[R]] = {
    Unmarshal(httpResponse.entity).to[ApiResponse[R]]
  }
}