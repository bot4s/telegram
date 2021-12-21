package com.bot4s.telegram.api.declarative

import cats.instances.list._
import cats.syntax.functor._
import cats.syntax.flatMap._
import cats.syntax.traverse._
import com.bot4s.telegram.api.BotBase

import scala.collection.mutable
import com.bot4s.telegram.models.ChatJoinRequest

/**
 * Declarative interface for processing join requests.
 */
trait JoinRequests[F[_]] extends BotBase[F] {

  private val joinRequests = mutable.ArrayBuffer[Action[F, ChatJoinRequest]]()

  /**
   * Executes 'action' for every join request
   */
  def onJoinRequest(action: Action[F, ChatJoinRequest]): Unit =
    joinRequests += action

  override def receiveJoinRequest(joinRequest: ChatJoinRequest): F[Unit] =
    for {
      _ <- joinRequests.toList.traverse(action => action(joinRequest))
      _ <- super.receiveJoinRequest(joinRequest)
    } yield ()

}
