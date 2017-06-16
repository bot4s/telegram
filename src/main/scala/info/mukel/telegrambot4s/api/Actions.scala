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

  private val actions = mutable.ArrayBuffer[MessageAction]()

  private def wrapFilteredAction(filter: MessageFilter, action: MessageAction): MessageAction = {
    msg => if (filter(msg)) action(msg)
  }

  abstract override def onMessage(msg: Message): Unit = {
    for (action <- actions)
      action(msg)

    // Fallback to upper level to preserve trait stack-ability
    super.onMessage(msg)
  }

  /**
    * Filter incoming messages and triggers custom actions.
    *
    * @param filter Message predicate, should not have side effects; every incoming message will be tested,
    *               it must be as cheap as possible.
    *
    * @param action Action to perform if the incoming message pass the filter.
    */
  def when(filter: MessageFilter)(action: MessageAction): Unit = {
    actions += wrapFilteredAction(filter, action)
  }

  /**
    * Executes 'action' for every incoming message.
    */
  def foreachMessage(action: MessageAction): Unit = {
    actions += action
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
