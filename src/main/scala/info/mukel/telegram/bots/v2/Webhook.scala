package info.mukel.telegram.bots.v2

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.stream.scaladsl.Sink
import info.mukel.telegram.bots.v2.api.Implicits._
import info.mukel.telegram.bots.v2.api.Jsonification
import info.mukel.telegram.bots.v2.methods.SetWebhook
import info.mukel.telegram.bots.v2.model.Update

/** Webhook
  *
  * Spawns a local server to receive updates.
  * Automatically registers the webhook on run().
  */
trait Webhook extends TelegramBot with Jsonification {
  def port: Int
  def webhookUrl: String

  private val route = pathEndOrSingleSlash {
      entity(as[Update]) {
        update =>
          handleUpdate(update)
          complete(StatusCodes.OK)
      }
    }

  private[this] val bindingFuture = Http().bind("::0", port) // All IPv4/6 interfaces
    .to(Sink.foreach(_.handleWith(route)))

  override def run(): Unit = {
    api.request(SetWebhook(webhookUrl))
      .foreach { success =>
        if (success)
          bindingFuture.run()
      }
  }
}
