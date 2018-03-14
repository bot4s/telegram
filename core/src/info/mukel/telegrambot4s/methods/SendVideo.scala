package info.mukel.telegrambot4s.methods

import info.mukel.telegrambot4s.methods.ParseMode.ParseMode
import info.mukel.telegrambot4s.models.{ChatId, InputFile, Message, ReplyMarkup}

/** Use this method to send video files, Telegram clients support mp4 videos (other formats may be sent as Document).
  * On success, the sent Message is returned.
  * Bots can currently send video files of up to 50 MB in size, this limit may be changed in the future.
  *
  * @param chatId               Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param video                InputFile or String Video to send. Pass a file_id as String to send a video that exists on the Telegram servers (recommended),
  *                             pass an HTTP URL as a String for Telegram to get a video from the Internet, or upload a new video using multipart/form-data.
  * @param duration             Integer Optional Duration of sent video in seconds
  * @param width                Integer Optional Video width
  * @param height               Integer Optional Video height
  * @param caption              String Optional Video caption (may also be used when resending videos by file_id), 0-200 characters
  * @param parseMode            String Optional Send Markdown or HTML, if you want Telegram apps to show bold, italic,
  *                             fixed-width text or inline URLs in the media caption.
  * @param supportsStreaming    Boolean Optional Pass True, if the uploaded video is suitable for streaming
  * @param disableNotification  Boolean Optional Sends the message silently.
  *                             iOS users will not receive a notification, Android users will receive a notification with no sound.
  * @param replyToMessageId     Integer Optional If the message is a reply, ID of the original message
  * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply Optional Additional interface options.
  *                             A JSON-serialized object for an inline keyboard, custom reply keyboard,
  *                             instructions to hide reply keyboard or to force a reply from the user.
  */
case class SendVideo(
                      chatId              : ChatId,
                      video               : InputFile,
                      duration            : Option[Int] = None,
                      width               : Option[Int] = None,
                      height              : Option[Int] = None,
                      caption             : Option[String] = None,
                      parseMode           : Option[ParseMode] = None,
                      supportsStreaming   : Option[Boolean] = None,
                      disableNotification : Option[Boolean] = None,
                      replyToMessageId    : Option[Long] = None,
                      replyMarkup         : Option[ReplyMarkup] = None
                    ) extends ApiRequestMultipart[Message]
