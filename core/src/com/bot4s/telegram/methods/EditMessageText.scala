package com.bot4s.telegram.methods

import ParseMode.ParseMode
import com.bot4s.telegram.models.{ Message, ReplyMarkup }
import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to edit text messages sent by the bot or via the bot (for inline bots).
 * On success, if edited message is sent by the bot, the edited Message is returned, otherwise True is returned.
 *
 * @param chatId                 Integer or String Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId              Integer Required if inline_message_id is not specified. Unique identifier of the sent message
 * @param inlineMessageId        String Required if chat_id and message_id are not specified. Identifier of the inline message
 * @param text                   String New text of the message
 * @param parseMode              String Optional Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
 * @param disableWebPagePreview  Boolean Optional Disables link previews for links in this message
 * @param replyMarkup            InlineKeyboardMarkup Optional A JSON-serialized object for an inline keyboard.
 */
case class EditMessageText(
  chatId: Option[ChatId] = None,
  messageId: Option[Int] = None,
  inlineMessageId: Option[String] = None,
  text: String,
  parseMode: Option[ParseMode] = None,
  disableWebPagePreview: Option[Boolean] = None,
  replyMarkup: Option[ReplyMarkup] = None
) extends JsonRequest {
  type Response = Either[Boolean, Message]

  if (inlineMessageId.isEmpty) {
    require(chatId.isDefined, "Required if inlineMessageId is not specified")
    require(messageId.isDefined, "Required if inlineMessageId is not specified")
  }

  if (chatId.isEmpty && messageId.isEmpty)
    require(inlineMessageId.isDefined, "Required if chatId and messageId are not specified")
}

object EditMessageText {
  implicit val customConfig: Configuration                      = Configuration.default.withSnakeCaseMemberNames
  implicit val editMessageTextEncoder: Encoder[EditMessageText] = deriveConfiguredEncoder[EditMessageText]
}
