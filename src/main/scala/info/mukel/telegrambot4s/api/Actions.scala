package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.methods.ParseMode.ParseMode
import info.mukel.telegrambot4s.methods.SendMessage
import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.models.{Message, ReplyMarkup}

import scala.collection.mutable
import scala.concurrent.Future

/**
  * Filter incoming messages and run custom actions.
  */
trait Actions extends BotBase {

  type Action = Message => Unit
  type MessageFilter = Message => Boolean

  private val actions = mutable.ArrayBuffer[(MessageFilter, Action)]()

  abstract override def onMessage(msg: Message): Unit = {
    for {
      (filter, action) <- actions
      if filter(msg)
    } /* do */ {
      action(msg)
    }
    // Fallback to upper level to preserve trait stack-ability
    super.onMessage(msg)
  }

  /**
    * Filter incoming messages and triggers custom actions.
    *
    * @param filter Message predicate, should not have side effects; every incoming message will be tested,
    *               so it must be as cheap as possible.
    *
    * @param action Action to perform if the incoming message pass the filter.
    */
  def when(filter: MessageFilter)(action: Action): Unit = {
    actions += (filter -> action)
  }

  /**
    * Send quick text replies. Supports Markdown formatting and markups.
    */
  def reply(text                  : String,
            parseMode             : Option[ParseMode] = None,
            disableWebPagePreview : Option[Boolean] = None,
            disableNotification   : Option[Boolean] = None,
            replyToMessageId      : Option[Long] = None,
            replyMarkup           : Option[ReplyMarkup] = None)
           (implicit message: Message): Future[Message] = {
    request(
      SendMessage(
        message.source,
        text,
        parseMode,
        disableWebPagePreview,
        disableNotification,
        replyToMessageId,
        replyMarkup
      )
    )
  }
}
