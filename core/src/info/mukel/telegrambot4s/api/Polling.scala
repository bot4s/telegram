package info.mukel.telegrambot4s.api

import com.typesafe.scalalogging.Logger
import info.mukel.telegrambot4s.methods.{DeleteWebhook, GetUpdates}
import info.mukel.telegrambot4s.models.Update

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}
import scala.util.control.NonFatal

/** Provides updates by (long) polling Telegram servers.
  *
  * See [[https://en.wikipedia.org/wiki/Push_technology#Long_polling wiki]].
  *
  * It relies on the underlying HTTP client which should support a timeout
  * allowing the connection to idle for at least 'pollingTimeout' seconds.
  */
trait Polling extends BotBase with BotExecutionContext {

  private val logger = Logger("Polling")

  private type OffsetUpdates = (Option[Long], Seq[Update])

  /**
    * The polling method, overload for custom behavior, e.g. back-off, retries...
    *
    * @param offset
    */
  def pollingGetUpdates(offset: Option[Long]): Future[Seq[Update]] = {
    request(
      GetUpdates(
        offset,
        timeout = Some(pollingTimeout),
        allowedUpdates = allowedUpdates
      ))
  }

  /**
    * Long-polling timeout in seconds.
    *
    * Specifies the amount of time the connection will be idle/waiting if there are no updates.
    */
  def pollingTimeout: Int = 45

  @volatile var polling: Future[Unit] = _

  private def poll(seed: Future[OffsetUpdates]): Future[OffsetUpdates] = {
    seed flatMap {
      case (offset, updates) =>

        val maxOffset = updates.map(_.updateId)
          .foldLeft(offset) {
            (acc, e) =>
              if (acc.isEmpty)
                Some(e)
              else
                acc.map(_ max e)
          }

        // Spawn next request before processing updates.
        val f = if (polling == null) seed
        else
          poll(
            pollingGetUpdates(maxOffset.map(_ + 1)).recover {
              case NonFatal(e) =>
                logger.error("GetUpdates failed", e)
                Seq.empty[Update]
            }.map((maxOffset, _))
          )

        for (u <- updates) {
          try {
            receiveUpdate(u)
          } catch {
            case NonFatal(e) =>
              logger.error("receiveUpdate failed", e)
          }
        }

        f
    }
  }

  abstract override def run(): Future[Unit] = synchronized {
    if (polling != null) {
      throw new RuntimeException("Bot is already running!")
    }
    super.run()
    Await.result(request(DeleteWebhook), Duration.Inf) match {
      case true =>
        logger.info(s"Starting (long) polling: timeout=$pollingTimeout seconds")
        polling = poll(Future.successful((None, Seq()))).map(_ => ())
        polling.onComplete {
          case _ => logger.info("Long polling terminated")
        }
        polling
      case false =>
        val msg = "Failed to clear webhook: DeleteWebhook returned false"
        logger.error(msg)
        throw new RuntimeException(msg)
    }
  }

  abstract override def shutdown(): Unit = synchronized {
    if (polling == null) {
      throw new RuntimeException("Bot is not running")
    }
    super.shutdown()
    polling = null
  }
}
