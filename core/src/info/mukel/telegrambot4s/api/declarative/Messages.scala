package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.api.BotBase
import info.mukel.telegrambot4s.methods.ParseMode.ParseMode
import info.mukel.telegrambot4s.methods.{ParseMode, SendMessage}
import info.mukel.telegrambot4s.models.{Message, ReplyMarkup}

import scala.collection.mutable
import scala.concurrent.Future

/**
  * Declarative helpers for processing incoming messages.
  */
trait Messages extends BotBase {

  private val messageActions = mutable.ArrayBuffer[Action[Message]]()
  private val editedMessageActions = mutable.ArrayBuffer[Action[Message]]()

  /**
    * Executes `action` for every incoming message.
    */
  def onMessage(action: Action[Message]): Unit = {
    messageActions += action
  }

  /**
    * Executes `action` for every incoming edited message event.
    */
  def onEditedMessage(action: Action[Message]): Unit = {
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
            replyToMessageId      : Option[Int] = None,
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

  /**
    * Sends text replies in Markdown format.

    * @param text                   Text of the message to be sent
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
  def replyMd(text                  : String,
            disableWebPagePreview : Option[Boolean] = None,
            disableNotification   : Option[Boolean] = None,
            replyToMessageId      : Option[Int] = None,
            replyMarkup           : Option[ReplyMarkup] = None)
           (implicit message: Message): Future[Message] = {
    reply(
      text,
      Some(ParseMode.Markdown),
      disableWebPagePreview,
      disableNotification,
      replyToMessageId,
      replyMarkup
    )(message)
  }

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
    super.receiveEditedMessage(msg)
  }

  /**
    * Generic extractor for messages.
    *
    * @example {{{
    *   onCommand('hello) { implicit msg =>
    *     using(_.from) {
    *       user =>
    *         reply(s"Hello ${user.firstName}!")
    *     }
    *   }
    * }}}
    */
  def using[T](extractor: Extractor[Message, T])(actionT: Action[T])(implicit msg: Message): Unit = {
    extractor(msg).foreach(actionT)
  }
}
