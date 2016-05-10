package info.mukel.telegram.bots.v2

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import info.mukel.telegram.bots.v2.methods.{GetUpdates, SetWebhook}
import info.mukel.telegram.bots.v2.api.Implicits._
import info.mukel.telegram.bots.v2.api.TelegramApiAkka
import info.mukel.telegram.bots.v2.model.Update

import scala.concurrent.Future

/** Polling
  *
  * Provides updates by polling Telegram servers.
  * When idle, it won't flood the server, it will send at most 3 queries per minute, still
  * the responses are instantaneous.
  */
trait Polling extends TelegramBot {

  private[this] type OffsetUpdates = Future[(Long, Seq[Update])]

  private[this] val seed = Future.successful((0L, Seq.empty[Update]))

  private[this] val iterator = Iterator.iterate[OffsetUpdates](seed) {
    _ flatMap {
      case (prevOffset, prevUpdates) =>
        val curOffset = prevUpdates
          .map(_.updateId)
          .fold(prevOffset)(_ max _)

        api.request(GetUpdates(curOffset + 1, timeout = 20))
          .recover {
            case e: Exception =>
              // TODO: Log error
              Seq.empty[Update]
          }
          .map { (curOffset, _) }
    }
  }

  private[this] val updateGroups =
    Source.fromIterator(() => iterator)
      .mapAsync(1)(identity)
      .map(_._2)

  private[this] val updates = updateGroups.mapConcat(_.to[collection.immutable.Seq])

  override def run(): Unit = {
    api.request(SetWebhook(None))
      .foreach {
        case true => updates.runForeach(handleUpdate)
      }
  }
}
