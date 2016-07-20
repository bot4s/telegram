package info.mukel.telegrambot4s.api

import akka.stream.scaladsl.Source
import info.mukel.telegrambot4s.methods.{GetUpdates, SetWebhook}
import info.mukel.telegrambot4s.models.Update
import info.mukel.telegrambot4s.Implicits._

import scala.concurrent.Future
import scala.util.{Failure, Success}

/** Provides updates by polling Telegram servers.
  *
  * When idle, it won't flood the server, it will send at most 3 queries per minute, still
  * the responses are instantaneous.
  */
trait Polling {
  this: TelegramBot =>

  private[this] val updates = {
    type OffsetUpdates = Future[(Long, Seq[Update])]

    val seed = Future.successful((0L, Seq.empty[Update]))

    val iterator = Iterator.iterate[OffsetUpdates](seed) {
      _ flatMap {
        case (prevOffset, prevUpdates) =>
          val curOffset = prevUpdates
            .map(_.updateId)
            .fold(prevOffset)(_ max _)

          api.request(GetUpdates(curOffset + 1, timeout = 20))
            .recover {
              case e: Exception =>
                log.error(e, "GetUpdates failed")
                Seq.empty[Update]
            }
            .map { (curOffset, _) }
      }
    }

    val updateGroups =
      Source.fromIterator(() => iterator)
        .mapAsync(1)(identity)
        .map(_._2)

    /* return */ updateGroups.mapConcat(_.to[collection.immutable.Seq])
  }

  override def run(): Unit = {
    api.request(SetWebhook(None)).onComplete {
      case Success(true) => updates.runForeach(handleUpdate)
      case Success(false) => log.error("Failed to clear webhook")
      case Failure(e) => log.error(e, "Failed to clear webhook")
    }
  }
}
