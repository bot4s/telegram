package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{Message, ReplyMarkup}
import com.bot4s.telegram.models.ChatId

/** Use this method to send point on the map.
  * On success, the sent Message is returned.
  *
  * @param chatId               Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param latitude             Float number Latitude of location
  * @param longitude            Float number Longitude of location
  * @param livePeriod           Integer Optional Period in seconds for which the location will be updated
  *                             (see Live Locations, should be between 60 and 86400.
  * @param disableNotification  Boolean Optional Sends the message silently.
  *                             iOS users will not receive a notification, Android users will receive a notification with no sound.
  * @param replyToMessageId     Integer Optional If the message is a reply, ID of the original message
  * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply Optional Additional interface options.
  *                             A JSON-serialized object for an inline keyboard, custom reply keyboard,
  *                             instructions to hide reply keyboard or to force a reply from the user.
  */
case class SendLocation(
                         chatId              : ChatId,
                         latitude            : Double,
                         longitude           : Double,
                         livePeriod          : Option[Int] = None,
                         disableNotification : Option[Boolean] = None,
                         replyToMessageId    : Option[Int] = None,
                         replyMarkup         : Option[ReplyMarkup] = None
                       ) extends JsonRequest[Message]
