package com.bot4s.telegram.methods

import ParseMode.ParseMode
import com.bot4s.telegram.models.{ Message, ReplyMarkup }
import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to edit captions of messages sent by the bot or via the bot (for inline bots).
 * On success, if edited message is sent by the bot, the edited Message is returned, otherwise True is returned.
 *
 * @param chatId           Integer or String No Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId        Integer No Required if inline_message_id is not specified. Unique identifier of the sent message
 * @param inlineMessageId  String No Required if chat_id and message_id are not specified. Identifier of the inline message
 * @param caption          String Optional New caption of the message
 * @param parseMode            String Optional Send Markdown or HTML, if you want Telegram apps to show bold, italic,
 *                             fixed-width text or inline URLs in the media caption.
 * @param replyMarkup      InlineKeyboardMarkup Optional A JSON-serialized object for an inline keyboard.
 */
case class EditMessageCaption(
  chatId: Option[ChatId] = None,
  messageId: Option[Int] = None,
  inlineMessageId: Option[String] = None,
  caption: Option[String] = None,
  parseMode: Option[ParseMode] = None,
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

object EditMessageCaption {
  implicit val customConfig: Configuration                            = Configuration.default.withSnakeCaseMemberNames
  implicit val editMessageCaptionEncoder: Encoder[EditMessageCaption] = deriveConfiguredEncoder[EditMessageCaption]
}
