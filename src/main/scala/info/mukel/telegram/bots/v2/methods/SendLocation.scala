package info.mukel.telegram.bots.v2.methods

import info.mukel.telegram.bots.v2.model.{InputFile, Message, ReplyMarkup}

/** sendLocation
  *
  * Use this method to send point on the map. On success, the sent Message is returned.
  *
  * @param chatId               Integer or String	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param latitude             Float number	Latitude of location
  * @param longitude            Float number	Longitude of location
  * @param disableNotification  Boolean	Optional	Sends the message silently. iOS users will not receive a notification, Android users will receive a notification with no sound.
  * @param replyToMessageId     Integer	Optional	If the message is a reply, ID of the original message
  * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to hide reply keyboard or to force a reply from the user.
  */
case class SendLocation(
                         chatId              : Either[Long, String],
                         latitude            : Double,
                         longitude           : Double,
                         duration            : Option[String] = None,
                         disableNotification : Option[Boolean] = None,
                         replyToMessageId    : Option[Long] = None,
                         replyMarkup         : Option[ReplyMarkup] = None
                       ) extends ApiRequestJson[Message]
