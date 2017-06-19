package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.api.BotBase
import info.mukel.telegrambot4s.methods.ParseMode.ParseMode
import info.mukel.telegrambot4s.methods.SendMessage
import info.mukel.telegrambot4s.models.{Message, ReplyMarkup}

import scala.collection.mutable
import scala.concurrent.Future

/**
  * Declarative helpers for processing incoming messages.
  */
trait Messages extends BotBase {

  private val messageActions = mutable.ArrayBuffer[MessageAction]()
  private val editedMessageActions = mutable.ArrayBuffer[MessageAction]()

  abstract override def receiveMessage(msg: Message): Unit = {
    for (action <- messageActions)
      action(msg)

    // Fallback to upper level to preserve trait stack-ability
    super.receiveMessage(msg)
  }

  abstract override def receiveEditedMessage(msg: Message): Unit = {
    for (action <- editedMessageActions)
      action(msg)

    // Fallback to upper level to preserve trait stack-ability
    super.receiveMessage(msg)
  }

  /**
    * Filter incoming messages and triggers custom actions.
    *
    * @param filter Message predicate, should not have side effects; every incoming message will be tested,
    *               it must be as cheap as possible.
    *
    * @param action Action to perform if the incoming message pass the filter.
    */
  def whenMessage(filter: MessageFilter)(action: MessageAction): Unit = {
    messageActions += wrapFilteredAction(filter, action)
  }

  /**
    * Filter edited messages and triggers custom actions.
    *
    * @param filter Message predicate, should not have side effects; every incoming message will be tested,
    *               it must be as cheap as possible.
    *
    * @param action Action to perform if the incoming message pass the filter.
    */
  def whenEditedMessage(filter: MessageFilter)(action: MessageAction): Unit = {
    editedMessageActions += wrapFilteredAction(filter, action)
  }

  /**
    * Executes `action` for every incoming message.
    */
  def onMessage(action: MessageAction): Unit = {
    messageActions += action
  }

  /**
    * Executes `action` for every incoming edited message event.
    */
  def onEditedMessage(action: MessageAction): Unit = {
    editedMessageActions += action
  }

  /**
    * Sends text replies. Supports Markdown/HTML formatting and markups.
    *
    * '''Note:'''
    *
    *    Only the tags mentioned above are currently supported.
    *    Tags must not be nested.
    *    All <, > and & symbols that are not a part of a tag or an HTML entity must be replaced with the corresponding HTML entities (< with &lt;, > with &gt; and & with &amp;).
    *    All numerical HTML entities are supported.
    *    The API currently supports only the following named HTML entities: &lt;, &gt;, &amp; and &quot;.
    *
    * @param text                   Text of the message to be sent
    * @param parseMode              Optional Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
    * @param disableWebPagePreview  Optional Disables link previews for links in this message
    * @param disableNotification    Optional Sends the message silently. iOS users will not receive a notification, Android users will receive a notification with no sound.
    * @param replyToMessageId       Optional If the message is a reply, ID of the original message
    * @param replyMarkup  [[info.mukel.telegrambot4s.models.InlineKeyboardMarkup]] or
    *                     [[info.mukel.telegrambot4s.models.ReplyKeyboardMarkup]] or
    *                     [[info.mukel.telegrambot4s.models.ReplyKeyboardRemove]] or
    *                     [[info.mukel.telegrambot4s.models.ForceReply]]
    *                     Optional Additional interface options.
    *                     A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to hide reply keyboard or to force a reply from the user.
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
