package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ Message, ReplyMarkup }

/**
 * Use this method to send a game.
 * On success, the sent Message is returned.
 *
 * @param chatId               Integer	Yes	Unique identifier for the target chat
 * @param gameShortName        String Short name of the game, serves as the unique identifier for the game. Set up your games via Botfather.
 * @param disableNotification  Boolean Optional Sends the message silently.
 *                             iOS users will not receive a notification, Android users will receive a notification with no sound.
 * @param protectContent       Boolean Optional Protects the contents of the sent message from forwarding and saving
 * @param replyToMessageId     Integer Optional If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Boolean optional Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup          InlineKeyboardMarkup Optional A JSON-serialized object for an inline keyboard.
 *                             If empty, one 'Play game_title' button will be shown.
 *                             If not empty, the first button must launch the game.
 */
case class SendGame(
  chatId: Long,
  gameShortName: String,
  disableNotification: Option[Boolean] = None,
  protectContent: Option[Boolean] = None,
  replyToMessageId: Option[Int] = None,
  allowSendingWithoutReply: Option[Boolean] = None,
  replyMarkup: Option[ReplyMarkup] = None
) extends JsonRequest[Message]
