package info.mukel.telegrambot4s.api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import info.mukel.telegrambot4s.marshalling.HttpMarshalling
import info.mukel.telegrambot4s.methods.SetWebhook
import info.mukel.telegrambot4s.models.Update

import scala.util.control.NonFatal
import scala.util.{Failure, Success}

/** Uses a webhook (as an alternative to polling) to receive updates.
  *
  * Automatically registers the webhook on run().
  */
trait Webhook extends WebRoutes {
  _ : BotBase with AkkaImplicits with BotExecutionContext =>

  import HttpMarshalling._
  import akka.http.scaladsl.server.Directives._

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

  abstract override val routes: Route =  webhookRoute ~ super.routes

  abstract override def run(): Unit = {
    request(SetWebhook(webhookUrl, allowedUpdates = allowedUpdates))
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
