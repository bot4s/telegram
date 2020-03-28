package com.bot4s.telegram.api.declarative

import cats.instances.list._
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.traverse._
import com.bot4s.telegram.api.BotBase
import com.bot4s.telegram.models.Message

import scala.collection.mutable

/**
  * Declarative helpers for processing channel posts.
  */
trait ChannelPosts[F[_]] extends BotBase[F] {

  private val channelPostActions = mutable.ArrayBuffer[Action[F, Message]]()
  private val editedChannelPostActions =
    mutable.ArrayBuffer[Action[F, Message]]()

  /**
    * Executes `action` for every channel post.
    */
  def onChannelPost(action: Action[F, Message]): Unit = {
    channelPostActions += action
  }

  /**
    * Executes `action` for every incoming edited channel post.
    */
  def onEditedChannelPost(action: Action[F, Message]): Unit = {
    editedChannelPostActions += action
  }

  override def receiveChannelPost(msg: Message): F[Unit] =
    for {
      _ <- channelPostActions.toList.traverse(action => action(msg))
      _ <- super.receiveChannelPost(msg)
    } yield ()

  override def receiveEditedChannelPost(msg: Message): F[Unit] =
    for {
      _ <- editedChannelPostActions.toList.traverse(action => action(msg))
      _ <- super.receiveEditedChannelPost(msg)
    } yield ()
}
