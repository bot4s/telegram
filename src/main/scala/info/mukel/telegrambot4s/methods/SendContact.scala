package info.mukel.telegrambot4s.methods

import info.mukel.telegrambot4s.models.{Message, ReplyMarkup}

/** Use this method to send phone contacts. On success, the sent Message is returned.
  *
  * @param chatId               Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param phoneNumber          String Contact's phone number
  * @param firstName            String Contact's first name
  * @param lastName             String Optional Contact's last name
  * @param disableNotification  Boolean Optional Sends the message silently. iOS users will not receive a notification, Android users will receive a notification with no sound.
  * @param replyToMessageId     Integer Optional If the message is a reply, ID of the original message
  * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply Optional Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
  */
case class SendContact(
                      chatId              : Long Either String,
                      phoneNumber         : String,
                      firstName           : String,
                      lastName            : Option[String] = None,
                      disableNotification : Option[Boolean] = None,
                      replyToMessageId    : Option[Long] = None,
                      replyMarkup         : Option[ReplyMarkup] = None
                      ) extends ApiRequestJson[Message]
