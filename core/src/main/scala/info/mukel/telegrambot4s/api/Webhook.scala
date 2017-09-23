package info.mukel.telegrambot4s.api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import info.mukel.telegrambot4s.marshalling.AkkaHttpMarshalling._
import info.mukel.telegrambot4s.methods.SetWebhook
import info.mukel.telegrambot4s.models.{InputFile, Update}

import scala.util.control.NonFatal
import scala.util.{Failure, Success}

/** Uses a webhook (as an alternative to polling) to receive updates.
  *
  * Automatically registers the webhook on run().
  */
trait Webhook extends WebRoutes {
  _ : BotBase with AkkaImplicits with BotExecutionContext =>

  /** URL for the webhook.
    *
    * 'webhookUrl' must be consistent with 'webhookRoute' (by default '/').
    */
  val webhookUrl: String

  /**
    * Webhook route.
    *
    * 'webhookUrl/' by default.
    *
    * @return Route handler to process updates.
    */
  def webhookRoute: Route = pathEndOrSingleSlash(webhookReceiver)

  def webhookReceiver: Route = {
    entity(as[Update]) { update =>
      try {
        receiveUpdate(update)
      } catch {
        case NonFatal(e) =>
          logger.error("Caught exception in update handler", e)
      }
      complete(StatusCodes.OK)
    }
  }

  def certificate: Option[InputFile] = None

  abstract override def routes: Route =  webhookRoute ~ super.routes

  abstract override def run(): Unit = {
    request(SetWebhook(url = webhookUrl, certificate = certificate, allowedUpdates = allowedUpdates))
      .onComplete {
        case Success(true) =>
          super.run() // Spawn WebRoute

        case Success(false) =>
          logger.error("Failed to set webhook")

        case Failure(e) =>
          logger.error("Failed to set webhook", e)
      }
  }
}
