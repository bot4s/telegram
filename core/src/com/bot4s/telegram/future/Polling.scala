package com.bot4s.telegram.future

import com.bot4s.telegram.api.{ Polling => BasePolling }
import com.bot4s.telegram.methods.{ DeleteWebhook, GetMe }
import com.bot4s.telegram.models.User
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.Future
import scala.util.control.NonFatal
import com.bot4s.telegram.models.ParsedUpdate

trait Polling extends BasePolling[Future] with BotExecutionContext with StrictLogging {

  private type OffsetUpdates = (Option[Long], Seq[ParsedUpdate], User)

  @volatile private var polling: Future[Unit] = _

  private def poll(seed: Future[OffsetUpdates]): Future[OffsetUpdates] =
    seed.flatMap { case (offset, updates, user) =>
      val maxReceivedOffset = updates.map {
        case ParsedUpdate.Failure(id, _)  => id
        case ParsedUpdate.Success(update) => update.updateId
      }.max

      val maxOffset = Some(
        offset
          .fold(maxReceivedOffset)(_ max maxReceivedOffset)
      )

      // Spawn next request before processing updates.
      val f =
        if (polling == null) seed
        else
          poll(
            pollingGetUpdates(maxOffset.map(_ + 1)).recover { case NonFatal(e) =>
              logger.error("GetUpdates failed", e)
              Seq.empty[ParsedUpdate]
            }.map((maxOffset, _, user))
          )

      for (u <- updates) {
        try {
          u match {
            case ParsedUpdate.Success(u) =>
              receiveUpdate(u, Some(user))
            case _ => Future.unit
          }
        } catch {
          case NonFatal(e) =>
            // Log and swallow, exception handling should happen on receiveUpdate.
            logger.error(s"receiveUpdate failed while processing: $u", e)
        }
      }

      f
    }

  private def startPolling(user: User): Future[Unit] = {
    logger.info(s"Starting (long) polling: timeout=$pollingTimeout seconds")
    polling = poll(Future.successful((None, Seq(), user))).map(_ => ())
    polling.onComplete { case _ =>
      logger.info("Long polling terminated")
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
      getMe <- request(GetMe)
      p     <- startPolling(getMe)
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
