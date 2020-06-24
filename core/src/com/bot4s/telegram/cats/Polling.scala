package com.bot4s.telegram.cats

import cats.MonadError
import cats.instances.list._
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.traverse._
import com.bot4s.telegram.api.{Polling => BasePolling}
import com.bot4s.telegram.methods.{DeleteWebhook, GetMe}
import com.bot4s.telegram.models.User
import com.typesafe.scalalogging.StrictLogging

case class PollingState(botUser: User, offset: Option[Long])

trait Polling[F[_]] extends BasePolling[F] with StrictLogging {

  implicit val monad: MonadError[F, Throwable]

  private def poll(state: PollingState): F[Unit] =
    for {
      updates <- pollingGetUpdates(state.offset.map(_ + 1))
      _ <- updates.toList.traverse { update =>
        monad.handleErrorWith(receiveUpdate(update, Some(state.botUser))) { e =>
          logger.warn(s"Can not process updates $update", e)
          unit
        }
      }
      nextOffset = updates
        .foldLeft(state.offset) { case (acc, u) => Some(acc.fold(u.updateId)(u.updateId max _)) }
      _ <- poll(state.copy(offset = nextOffset))
    } yield ()

  def startPolling(): F[Unit] =
    request(DeleteWebhook).ifM(
      for {
        getMe <- request(GetMe)
        _ <- poll(PollingState(getMe, None))
      } yield (),
      monad.raiseError(new Exception("Can not remove webhook"))
    )

  override def run(): F[Unit] = startPolling()
}
