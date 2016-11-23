package info.mukel.telegrambot4s.methods

import info.mukel.telegrambot4s.models.{Message, ReplyMarkup}

/** Use this method to edit only the reply markup of messages sent by the bot or via the bot (for inline bots).
  * On success, if edited message is sent by the bot, the edited Message is returned, otherwise True is returned.
  *
  * @param chatId           Integer or String Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param messageId        Integer Required if inline_message_id is not specified. Unique identifier of the sent message
  * @param inlineMessageId  String Required if chat_id and message_id are not specified. Identifier of the inline message
  * @param replyMarkup      InlineKeyboardMarkup Optional A JSON-serialized object for an inline keyboard.
  */
case class EditMessageReplyMarkup(
                                 chatId          : Option[Long Either String] = None,
                                 messageId       : Option[Long] = None,
                                 inlineMessageId : Option[String] = None,
                                 replyMarkup     : Option[ReplyMarkup] = None
                                 ) extends ApiRequestJson[Message Either Boolean]
