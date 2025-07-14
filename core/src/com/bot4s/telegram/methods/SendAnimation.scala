package com.bot4s.telegram.methods

import com.bot4s.telegram.methods.ParseMode.ParseMode
import com.bot4s.telegram.models.{ ChatId, InputFile, Message, MessageEntity, ReplyMarkup }
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to send animation files (GIF or H.264/MPEG-4 AVC video without sound).
 * On success, the sent Message is returned.
 * Bots can currently send animation files of up to 50 MB in size, this limit may be changed in the future.
 *
 * @param chatId              Integer or String 	Yes 	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param animation           InputFile or String 	Yes 	Animation to send. Pass a file_id as String to send an animation that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get an animation from the Internet, or upload a new animation using multipart/form-data. More info on Sending Files »
 * @param duration            Integer 	Optional 	Duration of sent animation in seconds
 * @param width               Integer 	Optional 	Animation width
 * @param height              Integer 	Optional 	Animation height
 * @param thumbnail           InputFile or String 	Optional 	Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 90. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass "attach://<file_attach_name>" if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param caption             String 	Optional 	Animation caption (may also be used when resending animation by file_id), 0-200 characters
 * @param parseMode           String 	Optional 	Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.
 * @param captionEntities      A JSON-serialized list of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param disableNotification Boolean 	Optional 	Sends the message silently. Users will receive a notification with no sound.
 * @param protectContent       Boolean Optional Protects the contents of the sent message from forwarding and saving
 * @param replyToMessageId    Integer 	Optional 	If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Boolean optional Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup         InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply 	Optional 	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 * @param messageThreadId        Optional Integer. Unique identifier for the target message thread (topic) of the forum; for forum supergroups only,
 * @param hasSpoiler           Boolean Optional. Pass true if the photo needs to be covered with a spoiler animation
 */
case class SendAnimation(
  chatId: ChatId,
  animation: InputFile,
  duration: Option[Int] = None,
  width: Option[Int] = None,
  height: Option[Int] = None,
  thumbnail: Option[InputFile] = None,
  caption: Option[String] = None,
  parseMode: Option[ParseMode] = None,
  captionEntities: Option[List[MessageEntity]] = None,
  disableNotification: Option[Boolean] = None,
  protectContent: Option[Boolean] = None,
  replyToMessageId: Option[Int] = None,
  allowSendingWithoutReply: Option[Boolean] = None,
  replyMarkup: Option[ReplyMarkup] = None,
  messageThreadId: Option[Int] = None,
  hasSpoiler: Option[Boolean] = None
) extends MultipartRequest {
  type Response = Message
  override def getFiles: List[(String, InputFile)] =
    List("animation" -> animation) ++ (thumbnail.map(t => "thumbnail" -> t)).toList
}

object SendAnimation {
  implicit val customConfig: Configuration                  = Configuration.default.withSnakeCaseMemberNames
  implicit val sendAnimationEncoder: Encoder[SendAnimation] = deriveConfiguredEncoder[SendAnimation]
}
