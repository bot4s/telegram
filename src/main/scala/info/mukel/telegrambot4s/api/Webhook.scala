package info.mukel.telegrambot4s.api

import akka.NotUsed
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.IncomingConnection
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.stream.{KillSwitches, OverflowStrategy, UniqueKillSwitch}
import akka.stream.scaladsl.{Keep, Sink, Source}
import info.mukel.telegrambot4s.methods.SetWebhook
import info.mukel.telegrambot4s.models.Update
import info.mukel.telegrambot4s.Implicits._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

/** Spawns a local server to receive updates.
  * Automatically registers the webhook on run().
  */
trait Webhook {
  _ : BotBase with AkkaDefaults =>

  import Marshalling._

  def port: Int
  def webhookUrl: String
  def interfaceIp: String = "::0"

  private[this] val route = pathEndOrSingleSlash {
      entity(as[Update]) {
        update =>
          // Handle updates on its own execution context.
          Future {
            onUpdate(update)
          }
          complete(StatusCodes.OK)
      }
    }

  private val bindingFuture = Http().bind(interfaceIp, port)

  private var killSwitch: UniqueKillSwitch = _

  override def run(): Unit = {
    api.request(SetWebhook(webhookUrl)).onComplete {
      case Success(true) =>

        val source = bindingFuture
        val sink = Sink.foreach[IncomingConnection](_.handleWith(route))

        val (switch, done) =
          source.
            viaMat(KillSwitches.single)(Keep.right).
            toMat(sink)(Keep.both).run()

        killSwitch = switch

      case Success(false) => logger.error("Failed to clear webhook")
      case Failure(e) => logger.error("Failed to clear webhook", e)
    }
  }

  override def shutdown(): Unit = {
    killSwitch.shutdown()
  }
}
