package info.mukel.telegrambot4s.api

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Sink
import info.mukel.telegrambot4s.marshalling.HttpMarshalling
import info.mukel.telegrambot4s.methods.SetWebhook
import info.mukel.telegrambot4s.models.Update

import scala.concurrent.Future
import scala.util.{Failure, Success}

/** Spawns a server to receive updates.
  * Automatically registers the webhook on run().
  */
trait Webhook {
  _ : BotBase with AkkaDefaults =>

  import HttpMarshalling._

  val port: Int
  val webhookUrl: String
  val interfaceIp: String = "::0"

  private val route = pathEndOrSingleSlash {
    entity(as[Update]) {
      update =>

        try {
          onUpdate(update)
        } catch {
          case e: Exception =>
            logger.error("Caught exception in update handler", e)
        }

        complete(StatusCodes.OK)
    }
  }

  private val requestHandler: HttpRequest => Future[HttpResponse] = Route.asyncHandler(route)

  private lazy val bindingFuture: Future[Http.ServerBinding] = {
    Http()
      .bind(interfaceIp, port)
      .to(Sink.foreach(_ handleWithAsyncHandler requestHandler))
      .run()
  }

  override def run(): Unit = {
    request(SetWebhook(webhookUrl))
      .onComplete {
        case Success(true) =>
          logger.info(s"start on $interfaceIp:$port")
          bindingFuture // spawn lazy
        case Success(false) =>
          logger.error("Failed to set webhook")
        case Failure(e) =>
          logger.error("Failed to set webhook", e)
      }
  }

  override def shutdown(): Future[_] = {
    bindingFuture
      .flatMap(_.unbind())
      .flatMap(_ => system.terminate())
  }
}
