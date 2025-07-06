package com.bot4s.telegram.methods

import ChatAction.ChatAction
import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method when you need to tell the user that something is happening on the bot's side.
 * The status is set for 5 seconds or less (when a message arrives from your bot, Telegram clients clear its typing status).
 *
 * Example: The ImageBot needs some time to process a request and upload the image.
 * Instead of sending a text message along the lines of "Retrieving image, please wait...", the bot may use sendChatAction with action = upload_photo.
 * The user will see a "sending photo" status for the bot.
 * We only recommend using this method when a response from the bot will take a noticeable amount of time to arrive.
 *
 * @param chatId  Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param action  String Type of action to broadcast.
 *                Choose one, depending on what the user is about to receive:
 *                typing for text messages, upload_photo for photos, record_video or upload_video for videos, record_audio or upload_audio for audio files, upload_document for general files, find_location for location data.
 * @param messageThreadId Optional. Integer. Unique identifier for the target message thread; supergroups only
 */
case class SendChatAction(
  chatId: ChatId,
  action: ChatAction,
  messageThreadId: Option[Int] = None
) extends JsonRequest[Boolean]

object SendChatAction {
  implicit val customConfig: Configuration           = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[SendChatAction] = deriveConfiguredEncoder
}
