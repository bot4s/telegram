package info.mukel.telegrambot4s.api

import akka.http.scaladsl.model.StatusCodes
import info.mukel.telegrambot4s.marshalling.HttpMarshalling
import info.mukel.telegrambot4s.methods.SetWebhook
import info.mukel.telegrambot4s.models.Update

import scala.util.control.NonFatal
import scala.util.{Failure, Success}

/** Uses a webhook (as an alternative to polling) to receive updates.
  * Automatically registers the webhook on run().
  */
trait Webhook extends WebRoutes {
  _ : BotBase with AkkaImplicits =>

  import HttpMarshalling._

  /** URL for the webhook.    *
    * 'webhookUrl' must be consistent with 'webhookRoute' (by default '/').
    */
  val webhookUrl: String

  /**
    * Webhook handler.
    * @return Route handler to process updates.
    */
  def webhookRoute = pathEndOrSingleSlash {
    entity(as[Update]) {
      update =>
        try {
          onUpdate(update)
        } catch {
          case NonFatal(e) =>
            logger.error("Caught exception in update handler", e)
        }
        complete(StatusCodes.OK)
    }
  }

  override val routes =  webhookRoute ~ super.routes

  override def run(): Unit = {
    request(SetWebhook(webhookUrl))
      .onComplete {
        case Success(true) =>
          logger.info(s"start on $interfaceIp:$port")
          super.run()
        case Success(false) =>
          logger.error("Failed to set webhook")
        case Failure(e) =>
          logger.error("Failed to set webhook", e)
      }
  }
}
