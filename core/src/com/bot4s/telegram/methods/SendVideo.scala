package com.bot4s.telegram.methods

import ParseMode.ParseMode
import com.bot4s.telegram.models.{ Message, MessageEntity, ReplyMarkup }
import com.bot4s.telegram.models.{ ChatId, InputFile }

/**
 * Use this method to send video files, Telegram clients support mp4 videos (other formats may be sent as Document).
 * On success, the sent Message is returned.
 * Bots can currently send video files of up to 50 MB in size, this limit may be changed in the future.
 *
 * @param chatId               Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param video                InputFile or String Video to send. Pass a file_id as String to send a video that exists on the Telegram servers (recommended),
 *                             pass an HTTP URL as a String for Telegram to get a video from the Internet, or upload a new video using multipart/form-data.
 * @param duration             Integer Optional Duration of sent video in seconds
 * @param width                Integer Optional Video width
 * @param height               Integer Optional Video height
 * @param thumbnail            InputFile or String Optional Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side.
 *                             The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320.
 *                             Ignored if the file is not uploaded using multipart/form-data.
 *                             Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>.
 * @param caption              String Optional Video caption (may also be used when resending videos by file_id), 0-200 characters
 * @param parseMode            String Optional Send Markdown or HTML, if you want Telegram apps to show bold, italic,
 *                             fixed-width text or inline URLs in the media caption.
 * @param captionEntities      A JSON-serialized list of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param supportsStreaming    Boolean Optional Pass True, if the uploaded video is suitable for streaming
 * @param disableNotification  Boolean Optional Sends the message silently.
 *                             iOS users will not receive a notification, Android users will receive a notification with no sound.
 * @param protectContent       Boolean Optional Protects the contents of the sent message from forwarding and saving
 * @param replyToMessageId     Integer Optional If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply Optional Additional interface options.
 *                             A JSON-serialized object for an inline keyboard, custom reply keyboard,
 *                             instructions to hide reply keyboard or to force a reply from the user.
 * @param messageThreadId      Optional Integer. Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
 * @param hasSpoiler           Boolean Optional. Pass true if the photo needs to be covered with a spoiler animation
 */
case class SendVideo(
  chatId: ChatId,
  video: InputFile,
  duration: Option[Int] = None,
  width: Option[Int] = None,
  height: Option[Int] = None,
  thumbnail: Option[InputFile] = None,
  caption: Option[String] = None,
  parseMode: Option[ParseMode] = None,
  captionEntities: Option[List[MessageEntity]] = None,
  supportsStreaming: Option[Boolean] = None,
  disableNotification: Option[Boolean] = None,
  protectContent: Option[Boolean] = None,
  replyToMessageId: Option[Long] = None,
  allowSendingWithoutReply: Option[Boolean] = None,
  replyMarkup: Option[ReplyMarkup] = None,
  messageThreadId: Option[Int] = None,
  hasSpoiler: Option[Boolean] = None
) extends MultipartRequest[Message] {
  override def getFiles: List[(String, InputFile)] = List("video" -> video)
}
