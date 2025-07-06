package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ InlineKeyboardMarkup, Message }
import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to edit only the reply markup of messages sent by the bot or via the bot (for inline bots).
 * On success, if edited message is sent by the bot, the edited Message is returned, otherwise True is returned.
 *
 * @param chatId           Integer or String Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId        Integer Required if inline_message_id is not specified. Unique identifier of the sent message
 * @param inlineMessageId  String Required if chat_id and message_id are not specified. Identifier of the inline message
 * @param replyMarkup      InlineKeyboardMarkup Optional A JSON-serialized object for an inline keyboard.
 */
case class EditMessageReplyMarkup(
  chatId: Option[ChatId] = None,
  messageId: Option[Int] = None,
  inlineMessageId: Option[String] = None,
  replyMarkup: Option[InlineKeyboardMarkup] = None
) extends JsonRequest[Either[Boolean, Message]] {
  if (inlineMessageId.isEmpty) {
    require(chatId.isDefined, "Required if inlineMessageId is not specified")
    require(messageId.isDefined, "Required if inlineMessageId is not specified")
  }

  if (chatId.isEmpty && messageId.isEmpty)
    require(inlineMessageId.isDefined, "Required if chatId and messageId are not specified")
}

object EditMessageReplyMarkup {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val editMessageReplyMarkupEncoder: Encoder[EditMessageReplyMarkup] = deriveConfiguredEncoder[EditMessageReplyMarkup]
}
