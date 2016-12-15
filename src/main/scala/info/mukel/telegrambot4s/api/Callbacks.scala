package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.models.CallbackQuery

import scala.collection.mutable

/**
  * Abstraction for callbacks, allowing to filter events by tag.
  */
trait Callbacks extends BotBase {

  type Filter = CallbackQuery => Boolean
  type Action = CallbackQuery => Unit

  private val handlers = mutable.Map[Filter, Action]()

  def onCallbackWithTag(tag: String)(action: Action): Unit = {
    onCallback(_.data.map(_.startsWith(tag)).getOrElse(false))(action)
  }

  def onCallback(filter: Filter)(action: Action): Unit = {
    handlers += ((filter, action))
  }

  abstract override def onCallbackQuery(callbackQuery: CallbackQuery): Unit = {
    for {
      (filter, action) <- handlers
      if filter(callbackQuery)
    } yield
      action(callbackQuery)

    super.onCallbackQuery(callbackQuery)
  }
}
