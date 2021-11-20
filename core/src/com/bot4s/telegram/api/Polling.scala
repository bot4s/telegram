package com.bot4s.telegram.api

import com.bot4s.telegram.methods.GetUpdates
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.duration.{ Duration, _ }
import com.bot4s.telegram.models.ParsedUpdate

/**
 * Provides updates by (long) polling Telegram servers.
 *
 * See [[https://en.wikipedia.org/wiki/Push_technology#Long_polling wiki]].
 *
 * It relies on the underlying HTTP client which should support a timeout
 * allowing the connection to idle for a duration of at least 'pollingTimeout'.
 */
private[telegram] trait Polling[F[_]] extends BotBase[F] with StrictLogging {

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
  def pollingGetUpdates(offset: Option[Long]): F[Seq[ParsedUpdate]] =
    request(
      GetUpdates(
        offset,
        timeout = Some(pollingTimeout.toSeconds.toInt),
        allowedUpdates = allowedUpdates
      )
    )
}
