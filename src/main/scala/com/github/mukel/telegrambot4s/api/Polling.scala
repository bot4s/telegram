package com.github.mukel.telegrambot4s.api

import akka.stream.scaladsl.Source
import com.github.mukel.telegrambot4s._, models._, methods._, Implicits._

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
      .foreach { success =>
        if (success)
          updates.runForeach(handleUpdate)
      }
  }
}
