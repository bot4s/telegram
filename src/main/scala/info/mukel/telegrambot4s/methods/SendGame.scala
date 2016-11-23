package info.mukel.telegrambot4s.methods

import info.mukel.telegrambot4s.models.{Message, ReplyMarkup}

/** Use this method to send a game. On success, the sent Message is returned.
  *
  * @param chatId               Integer or String Yes Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param gameShortName        String Yes Short name of the game, serves as the unique identifier for the game. Set up your games via Botfather.
  * @param disableNotification  Boolean Optional Sends the message silently. iOS users will not receive a notification, Android users will receive a notification with no sound.
  * @param replyToMessageId     Integer Optional If the message is a reply, ID of the original message
  * @param replyMarkup          InlineKeyboardMarkup Optional A JSON-serialized object for an inline keyboard. If empty, one ‘Play game_title’ button will be shown. If not empty, the first button must launch the game.
  */
case class SendGame(
                     chatId              : Long Either String,
                     gameShortName       : String,
                     disableNotification : Option[Boolean] = None,
                     replyToMessageId    : Option[Long] = None,
                     replyMarkup         : Option[ReplyMarkup] = None
                   ) extends ApiRequestJson[Message]
