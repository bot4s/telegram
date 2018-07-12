package com.bot4s.telegram.api

import com.bot4s.telegram.methods.{DeleteWebhook, GetMe, GetUpdates}
import com.bot4s.telegram.models.Update
import slogging.StrictLogging

import scala.concurrent.Future
import scala.concurrent.duration.{Duration, _}
import scala.util.control.NonFatal

/** Provides updates by (long) polling Telegram servers.
  *
  * See [[https://en.wikipedia.org/wiki/Push_technology#Long_polling wiki]].
  *
  * It relies on the underlying HTTP client which should support a timeout
  * allowing the connection to idle for a duration of at least 'pollingTimeout'.
  */
trait Polling extends BotBase with BotExecutionContext with StrictLogging {

  private type OffsetUpdates = (Option[Long], Seq[Update])

  /**
    * Long-polling timeout in seconds.
    *
    * Specifies the amount of time the connection will be idle/waiting if there are no updates.
    */
  def pollingTimeout: Duration = 30.seconds

  /**
    * The polling method, overload for custom behavior, e.g. back-off, retries...
    *
    * @param offset
    */
  def pollingGetUpdates(offset: Option[Long]): Future[Seq[Update]] = {
    request(
      GetUpdates(
        offset,
        timeout = Some(pollingTimeout.toSeconds.toInt),
        allowedUpdates = allowedUpdates
      ))
  }

  @volatile private var polling: Future[Unit] = _

  private def poll(seed: Future[OffsetUpdates]): Future[OffsetUpdates] = {
    seed.flatMap {
      case (offset, updates) =>

        val maxOffset = updates
          .map(_.updateId)
          .foldLeft(offset) {
            (acc, e) =>
              Some(acc.fold(e)(e max _))
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
              // Log and swallow, exception handling should happen on receiveUpdate.
              logger.error(s"receiveUpdate failed while processing: $u", e)
          }
        }

        f
    }
  }

  private def startPolling(): Future[Unit] = {
    logger.info(s"Starting (long) polling: timeout=$pollingTimeout seconds")
    polling = poll(Future.successful((None, Seq()))).map(_ => ())
    polling.onComplete {
      case _ => logger.info("Long polling terminated")
    }
    polling
  }

  override def run(): Future[Unit] = synchronized {
    if (polling != null) {
      throw new RuntimeException("Bot is already running")
    }
    for {
      deleted <- request(DeleteWebhook)
      if deleted
      getMe_ <- request(GetMe)
      _ = { getMe = getMe_ } // Initialize bot
      p <- startPolling()
    } yield {
      p
    }
  }

  override def shutdown(): Unit = synchronized {
    if (polling == null) {
      throw new RuntimeException("Bot is not running")
    }
    super.shutdown()
    polling = null
  }
}
