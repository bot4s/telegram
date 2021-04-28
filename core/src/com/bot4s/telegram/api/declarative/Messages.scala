package com.bot4s.telegram.api.declarative

import cats.instances.list._
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.traverse._
import com.bot4s.telegram.api.BotBase
import com.bot4s.telegram.methods.ParseMode.ParseMode
import com.bot4s.telegram.methods.{ ParseMode, SendMessage }
import com.bot4s.telegram.models.{ Message, ReplyMarkup, User }

import scala.collection.mutable

/**
 * Declarative helpers for processing incoming messages.
 */
trait Messages[F[_]] extends BotBase[F] {

  private val messageActions       = mutable.ArrayBuffer[Action[F, Message]]()
  private val editedMessageActions = mutable.ArrayBuffer[Action[F, Message]]()
  private val extMessageActions    = mutable.ArrayBuffer[Action[F, (Message, Option[User])]]()

  /**
   * Executes `action` for every incoming message.
   */
  def onMessage(action: Action[F, Message]): Unit =
    messageActions += action

  /**
   * Executes `action` for every incoming edited message event.
   */
  def onEditedMessage(action: Action[F, Message]): Unit =
    editedMessageActions += action

  def onExtMessage(action: Action[F, (Message, Option[User])]): Unit =
    extMessageActions += action

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
   * @param parseMode             Optional Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
   * @param disableWebPagePreview Optional Disables link previews for links in this message
   * @param disableNotification   Optional Sends the message silently. iOS users will not receive a notification, Android users will receive a notification with no sound.
   * @param replyToMessageId      Optional If the message is a reply, ID of the original message
   * @param replyMarkup           [[com.bot4s.telegram.models.InlineKeyboardMarkup]] or
   *                              [[com.bot4s.telegram.models.ReplyKeyboardMarkup]] or
   *                              [[com.bot4s.telegram.models.ReplyKeyboardRemove]] or
   *                              [[com.bot4s.telegram.models.ForceReply]]
   *                              Optional Additional interface options.
   *                              A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to hide reply keyboard or to force a reply from the user.
   */
  def reply(
    text: String,
    parseMode: Option[ParseMode] = None,
    disableWebPagePreview: Option[Boolean] = None,
    disableNotification: Option[Boolean] = None,
    replyToMessageId: Option[Int] = None,
    replyMarkup: Option[ReplyMarkup] = None
  )(implicit message: Message): F[Message] =
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

  /**
   * Sends text replies in Markdown format (using legacy format)
   *
   * @param text                     Text of the message to be sent
   * @param disableWebPagePreview Optional Disables link previews for links in this message
   * @param disableNotification   Optional Sends the message silently. iOS users will not receive a notification, Android users will receive a notification with no sound.
   * @param replyToMessageId      Optional If the message is a reply, ID of the original message
   * @param replyMarkup           [[models.InlineKeyboardMarkup]] or
   *                              [[models.ReplyKeyboardMarkup]] or
   *                              [[ReplyKeyboardRemove]] or
   *                              [[ForceReply]]
   *                              Optional Additional interface options.
   *                              A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to hide reply keyboard or to force a reply from the user.
   */
  def replyMd(
    text: String,
    disableWebPagePreview: Option[Boolean] = None,
    disableNotification: Option[Boolean] = None,
    replyToMessageId: Option[Int] = None,
    replyMarkup: Option[ReplyMarkup] = None
  )(implicit message: Message): F[Message] =
    reply(
      text,
      Some(ParseMode.Markdown),
      disableWebPagePreview,
      disableNotification,
      replyToMessageId,
      replyMarkup
    )(message)

  /**
   * Sends text replies in Markdown format (using the new format)
   *
   * @param text                     Text of the message to be sent
   * @param disableWebPagePreview Optional Disables link previews for links in this message
   * @param disableNotification   Optional Sends the message silently. iOS users will not receive a notification, Android users will receive a notification with no sound.
   * @param replyToMessageId      Optional If the message is a reply, ID of the original message
   * @param replyMarkup           [[models.InlineKeyboardMarkup]] or
   *                              [[models.ReplyKeyboardMarkup]] or
   *                              [[ReplyKeyboardRemove]] or
   *                              [[ForceReply]]
   *                              Optional Additional interface options.
   *                              A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to hide reply keyboard or to force a reply from the user.
   */
  def replyMdV2(
    text: String,
    disableWebPagePreview: Option[Boolean] = None,
    disableNotification: Option[Boolean] = None,
    replyToMessageId: Option[Int] = None,
    replyMarkup: Option[ReplyMarkup] = None
  )(implicit message: Message): F[Message] =
    reply(
      text,
      Some(ParseMode.MarkdownV2),
      disableWebPagePreview,
      disableNotification,
      replyToMessageId,
      replyMarkup
    )(message)

  override def receiveMessage(msg: Message): F[Unit] =
    for {
      _ <- messageActions.toList.traverse(action => action(msg))
      _ <- super.receiveMessage(msg)
    } yield ()

  override def receiveEditedMessage(msg: Message): F[Unit] =
    for {
      _ <- editedMessageActions.toList.traverse(action => action(msg))
      _ <- super.receiveEditedMessage(msg)
    } yield ()

  override def receiveExtMessage(extMessage: (Message, Option[User])): F[Unit] =
    for {
      _ <- extMessageActions.toList.traverse(action => action(extMessage))
      _ <- super.receiveExtMessage(extMessage)
    } yield ()

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
  def using[T](extractor: Extractor[Message, T])(actionT: Action[F, T])(implicit msg: Message): F[Unit] =
    extractor(msg).fold(unit)(actionT)
}
