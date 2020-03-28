package com.bot4s.telegram.api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.bot4s.telegram.future.BotExecutionContext
import com.bot4s.telegram.log.StrictLogging
import com.bot4s.telegram.methods.SetWebhook
import com.bot4s.telegram.models.{InputFile, Update}

import scala.concurrent.Future
import scala.util.control.NonFatal

/** Uses a webhook, as an alternative to polling, to receive updates.
  *
  * Automatically registers the webhook on run().
  */
trait Webhook extends WebRoutes {
  _: BotBase[Future]
    with BotExecutionContext
    with AkkaImplicits
    with StrictLogging =>

  import com.bot4s.telegram.marshalling._
  import com.bot4s.telegram.marshalling.AkkaHttpMarshalling._

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

  /**
    * Specify self-signed certificate file.
    * Check instructions at [[https://core.telegram.org/bots/self-signed Using self-signed certificates]].
    *
    * @return
    */
  def certificate: Option[InputFile] = None

  def webhookReceiver: Route = {
    entity(as[Update]) { update =>
      try {
        receiveUpdate(update, None)
      } catch {
        case NonFatal(e) =>
          logger.error("Caught exception in update handler", e)
      }
      complete(StatusCodes.OK)
    }
  }

  abstract override def routes: Route = webhookRoute ~ super.routes

  abstract override def run(): Future[Unit] = {
    request(
      SetWebhook(
        url = webhookUrl,
        certificate = certificate,
        allowedUpdates = allowedUpdates
      )
    ).flatMap {
      case true => super.run() // spawn WebRoutes
      case false =>
        logger.error("Failed to set webhook")
        throw new RuntimeException("Failed to set webhook")
    }
  }
}
