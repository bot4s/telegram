package info.mukel.telegrambot4s.methods

import info.mukel.telegrambot4s.models.Message

/** Use this method to set the score of the specified user in a game.
  *
  * On success, if the message was sent by the bot, returns the edited Message,
  * otherwise returns True.
  * Returns an error, if the new score is not greater than the user's current
  * score in the chat and force is False.
  *
  * @param userId             Integer Yes User identifier
  * @param score              Integer Yes New score, must be positive
  * @param force              Boolean Optional Pass True, if the high score is allowed to decrease.
  *                           This can be useful when fixing mistakes or banning cheaters
  * @param disableEditMessage Boolean Optional Pass True, if the game message should not be automatically edited to include the current scoreboard
  * @param chatId             Integer or String Optional Required if inline_message_id is not specified.
  *                           Unique identifier for the target chat (or username of the target channel in the format @channelusername)
  * @param messageId          Integer Optional Required if inline_message_id is not specified. Unique identifier of the sent message
  * @param inlineMessageId    String Optional Required if chat_id and message_id are not specified. Identifier of the inline message
  */
case class SetGameScore(
                       userId             : Long,
                       score              : Long,
                       force              : Option[Boolean] = None,
                       disableEditMessage : Option[Boolean] = None,
                       chatId             : Option[Either[Long, String]] = None,
                       messageId          : Option[Long] = None,
                       inlineMessageId    : Option[String] = None
                       ) extends ApiRequestJson[Either[Boolean, Message]] {

  if (inlineMessageId.isEmpty) {
    require(chatId.isDefined, "Required if inlineMessageId is not specified")
    require(messageId.isDefined, "Required if inlineMessageId is not specified")
  }

  if (chatId.isEmpty && messageId.isEmpty)
    require(inlineMessageId.isDefined, "Required if chatId and messageId are not specified")
}

