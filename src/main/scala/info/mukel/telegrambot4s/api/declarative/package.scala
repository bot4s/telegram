package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.models.{CallbackQuery, InlineQuery, Message}

package object declarative {
  type Action[T] = T => Unit
  type Filter[T] = T => Boolean
  type Args = Seq[String]

  type MessageAction = Action[Message]
  type MessageActionWith[T] = Message => T => Unit
  type MessageActionWithArgs = MessageActionWith[Args]
  type MessageFilter = Filter[Message]

  type CallbackQueryAction = Action[CallbackQuery]
  type CallbackQueryFilter = Filter[CallbackQuery]

  type InlineQueryAction = Action[InlineQuery]
  type InlineQueryFilter = Filter[InlineQuery]
  type InlineQueryActionWith[T] = InlineQuery => T => Unit
  type InlineQueryActionWithArgs = InlineQueryActionWith[Args]

  def wrapFilteredAction[T](filter: Filter[T], action: Action[T]): Action[T] = {
    msg =>
      if (filter(msg))
        action(msg)
  }
}
