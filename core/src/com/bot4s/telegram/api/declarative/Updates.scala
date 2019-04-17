package com.bot4s.telegram.api.declarative

import cats.instances.list._
import cats.syntax.functor._
import cats.syntax.flatMap._
import cats.syntax.traverse._
import com.bot4s.telegram.api.BotBase
import com.bot4s.telegram.models.{Update, User}

import scala.collection.mutable

/**
  * Declarative helpers for processing updates.
  */
trait Updates[F[_]] extends BotBase[F] {

  private val updateActions = mutable.ArrayBuffer[Action[F, Update]]()

  /**
    * Executes `action` for every update.
    */
  def onUpdate(action: Action[F, Update]): Unit = {
    updateActions += action
  }

  override def receiveUpdate(update: Update, botUser: Option[User]): F[Unit] =
    for {
      _ <- updateActions.toList.traverse(action => action(update))
      _ <- super.receiveUpdate(update, botUser)
    } yield ()
}
