package info.mukel.telegrambot4s.methods

import info.mukel.telegrambot4s.models.{Message, ReplyMarkup}

/** Use this method to edit captions of messages sent by the bot or via the bot (for inline bots).
  * On success, if edited message is sent by the bot, the edited Message is returned, otherwise True is returned.
  *
  * @param chatId           Integer or String No Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param messageId        Integer No Required if inline_message_id is not specified. Unique identifier of the sent message
  * @param inlineMessageId  String No Required if chat_id and message_id are not specified. Identifier of the inline message
  * @param caption          String Optional New caption of the message
  * @param replyMarkup      InlineKeyboardMarkup Optional A JSON-serialized object for an inline keyboard.
  */
case class EditMessageCaption(
                               chatId                : Option[Long Either String] = None,
                               messageId             : Option[Long] = None,
                               inlineMessageId       : Option[Long] = None,
                               caption               : Option[String] = None,
                               replyMarkup           : Option[ReplyMarkup] = None
                             ) extends ApiRequestJson[Message Either Boolean] {

  if (inlineMessageId.isEmpty) {
    require(chatId.isDefined, "Required if inlineMessageId is not specified")
    require(messageId.isDefined, "Required if inlineMessageId is not specified")
  }

  if (chatId.isEmpty && messageId.isEmpty)
    require(inlineMessageId.isDefined, "Required if chatId and messageId are not specified")
}
