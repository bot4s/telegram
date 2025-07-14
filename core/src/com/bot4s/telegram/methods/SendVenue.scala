package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ Message, ReplyMarkup }
import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to send information about a venue. On success, the sent Message is returned.
 *
 * @param chatId               Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param latitude             Float number Latitude of the venue
 * @param longitude            Float number Longitude of the venue
 * @param title                String Name of the venue
 * @param address              String Address of the venue
 * @param foursquareId         String Optional Foursquare identifier of the venue
 * @param foursquareType       String Optional. Foursquare type of the venue, if known. (For example, “arts_entertainment/default”, “arts_entertainment/aquarium” or “food/icecream”.)
 * @param googlePlaceId        String Optional Foursquare identifier of the venue
 * @param googlePlaceType      String Optional. Foursquare type of the venue, if known. (For example, “arts_entertainment/default”, “arts_entertainment/aquarium” or “food/icecream”.)
 * @param protectContent       Boolean Optional Protects the contents of the sent message from forwarding and saving
 * @param disableNotification  Boolean Optional Sends the message silently.
 *                             iOS users will not receive a notification, Android users will receive a notification with no sound.
 * @param replyToMessageId     Integer Optional If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Boolean optional Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply Optional Additional interface options.
 *                             A JSON-serialized object for an inline keyboard, custom reply keyboard,
 *                             instructions to hide reply keyboard or to force a reply from the user.
 * @param messageThreadId      Optional Integer. Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
 */
case class SendVenue(
  chatId: ChatId,
  latitude: Double,
  longitude: Double,
  title: String,
  address: String,
  foursquareId: Option[String] = None,
  foursquareType: Option[String] = None,
  googlePlaceId: Option[String] = None,
  googlePlaceType: Option[String] = None,
  duration: Option[String] = None,
  protectContent: Option[Boolean] = None,
  disableNotification: Option[Boolean] = None,
  replyToMessageId: Option[Int] = None,
  allowSendingWithoutReply: Option[Boolean] = None,
  replyMarkup: Option[ReplyMarkup] = None,
  messageThreadId: Option[Int] = None
) extends JsonRequest {
  type Response = Message
}

object SendVenue {
  implicit val customConfig: Configuration      = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[SendVenue] = deriveConfiguredEncoder
}
