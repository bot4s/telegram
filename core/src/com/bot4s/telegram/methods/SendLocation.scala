package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ Message, ReplyMarkup }
import com.bot4s.telegram.models.ChatId

/**
 * Use this method to send point on the map.
 * On success, the sent Message is returned.
 *
 * @param chatId               Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param latitude             Float number Latitude of location
 * @param longitude            Float number Longitude of location
 * @param horizontalAccuracy   Float number Optional The radius of uncertainty for the location, measured in meters; 0-1500
 * @param livePeriod           Integer Optional Period in seconds for which the location will be updated
 *                             (see Live Locations, should be between 60 and 86400.
 * @param heading              Integer Optional For live locations, a direction in which the user is moving, in degrees. Must be between 1 and 360 if specified.
 * @param proximityAlertRadius Integer Optional For live locations, a maximum distance for proximity alerts about approaching another chat member, in meters.
 *                             Must be between 1 and 100000 if specified.
 * @param disableNotification  Boolean Optional Sends the message silently.
 *                             iOS users will not receive a notification, Android users will receive a notification with no sound.
 * @param protectContent       Boolean Optional Protects the contents of the sent message from forwarding and saving
 * @param replyToMessageId     Integer Optional If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Boolean optional Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply Optional Additional interface options.
 *                             A JSON-serialized object for an inline keyboard, custom reply keyboard,
 *                             instructions to hide reply keyboard or to force a reply from the user.
 */
case class SendLocation(
  chatId: ChatId,
  latitude: Double,
  longitude: Double,
  horizontalAccuracy: Option[Double] = None,
  heading: Option[Int] = None,
  proximityAlertRadius: Option[Int] = None,
  livePeriod: Option[Int] = None,
  disableNotification: Option[Boolean] = None,
  protectContent: Option[Boolean] = None,
  replyToMessageId: Option[Int] = None,
  allowSendingWithoutReply: Option[Boolean] = None,
  replyMarkup: Option[ReplyMarkup] = None
) extends JsonRequest[Message]
