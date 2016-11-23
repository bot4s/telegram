package info.mukel.telegrambot4s.methods

import info.mukel.telegrambot4s.models.GameHighScore

/** Use this method to get data for high score tables. Will return the score of the specified user and several of his neighbors in a game. On success, returns an Array of GameHighScore objects.
  *
  * This method will currently return scores for the target user, plus two of his closest neighbors on each side. Will also return the top three users if the user and his neighbors are not among them. Please note that this behavior is subject to change.
  *
  * @param userId           Integer Yes Target user id
  * @param chatId           Integer or String Optional Required if inline_message_id is not specified. Unique identifier for the target chat (or username of the target channel in the format @channelusername)
  * @param messageId        Integer Optional Required if inline_message_id is not specified. Unique identifier of the sent message
  * @param inlineMessageId  String Optional Required if chat_id and message_id are not specified. Identifier of the inline message
  */
case class GetGameHighScores(
                            userId          : Long,
                            chatId          : Option[Long Either String] = None,
                            messageId       : Option[Long] = None,
                            inlineMessageId : Option[String] = None
                            ) extends ApiRequestJson[Seq[GameHighScore]]
