package info.mukel.telegrambot4s.methods

import info.mukel.telegrambot4s.models.Message

/** Use this method to forward messages of any kind. On success, the sent Message is returned.
  *
  * @param chatId               Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param fromChatId           Integer or String Unique identifier for the chat where the original message was sent (or channel username in the format @channelusername)
  * @param disableNotification  Boolean Optional Sends the message silently. iOS users will not receive a notification, Android users will receive a notification with no sound.
  * @param messageId            Integer Unique message identifier
  */
case class ForwardMessage(
                         chatId              : Long Either String,
                         fromChatId          : Long Either String,
                         disableNotification : Option[Boolean] = None,
                         messageId           : Long
                         ) extends ApiRequestJson[Message]
