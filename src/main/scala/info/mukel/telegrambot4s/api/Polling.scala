package info.mukel.telegrambot4s.api

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import info.mukel.telegrambot4s.methods.{GetUpdates, SetWebhook}
import info.mukel.telegrambot4s.models.Update
import info.mukel.telegrambot4s.Implicits._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

/** Provides updates by polling Telegram servers.
  *
  * When idle, it won't flood the server, it will send at most 3 queries per minute, still
  * the responses are instantaneous.
  */
trait Polling extends BotBase with AkkaDefaults {

  private val updates: Source[Update, NotUsed] = {
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
                logger.error("GetUpdates failed", e)
                Seq.empty[Update]
            }
            .map { (curOffset, _) }
      }
    }

    val parallelism = Runtime.getRuntime().availableProcessors()

    val updateGroups =
      Source.fromIterator(() => iterator)
        .mapAsync(parallelism)(identity)
        .map(_._2)

    updateGroups.mapConcat(_.to[collection.immutable.Seq])
  }

  override def run(): Unit = {
    api.request(SetWebhook(None)).onComplete {
      case Success(true) => updates.to(Sink.foreach(u => Future { onUpdate(u) })).run()
      case Success(false) => logger.error("Failed to clear webhook")
      case Failure(e) => logger.error("Failed to clear webhook", e)
    }
  }

  override def shutdown(): Unit = { /* TODO */ }
}
