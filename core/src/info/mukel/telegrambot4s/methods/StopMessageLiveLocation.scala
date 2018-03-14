package info.mukel.telegrambot4s.methods

import info.mukel.telegrambot4s.models.{ChatId, InlineKeyboardMarkup, Message}

/**
  * Use this method to stop updating a live location message sent by the bot or via the bot (for inline bots) before live_period expires.
  * On success, if the message was sent by the bot, the sent Message is returned, otherwise True is returned.
  *
  * @param chatId           Integer or String Optional Required if inline_message_id is not specified.
  *                         Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param messageId        Integer Optional Required if inline_message_id is not specified. Identifier of the sent message
  * @param inlineMessageId  String Optional Required if chat_id and message_id are not specified. Identifier of the inline message
  * @param replyMarkup      InlineKeyboardMarkup Optional	A JSON-serialized object for a new inline keyboard.
  */
case class StopMessageLiveLocation(
                                    chatId          : Option[ChatId] = None,
                                    messageId       : Option[Int] = None,
                                    inlineMessageId : Option[Int] = None,
                                    replyMarkup     : Option[InlineKeyboardMarkup] = None
                                  ) extends ApiRequestJson[Message Either Boolean]