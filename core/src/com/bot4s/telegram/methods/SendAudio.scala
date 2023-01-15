package com.bot4s.telegram.methods

import ParseMode.ParseMode
import com.bot4s.telegram.models.{ Message, MessageEntity, ReplyMarkup }
import com.bot4s.telegram.models.{ ChatId, InputFile }

/**
 * Use this method to send audio files, if you want Telegram clients to display them in the music player.
 * Your audio must be in the .mp3 format.
 * On success, the sent Message is returned.
 * Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
 *
 * For sending voice messages, use the sendVoice method instead.
 *
 * @param chatId               Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param audio                InputFile or String Audio file to send. Audio file to send.
 *                             Pass a file_id as String to send an audio file that exists on the Telegram servers (recommended),
 *                             pass an HTTP URL as a String for Telegram to get an audio file from the Internet, or upload a new one using multipart/form-data.
 * @param caption              String Optional Audio caption, 0-200 characters
 * @param parseMode            String Optional Send Markdown or HTML, if you want Telegram apps to show bold, italic,
 *                             fixed-width text or inline URLs in the media caption.
 * @param duration             Integer Optional Duration of the audio in seconds
 * @param performer            String Optional Performer
 * @param title                String Optional Track name
 * @param captionEntities      A JSON-serialized list of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param disableNotification  Boolean Optional Sends the message silently. iOS users will not receive a notification,
 *                             Android users will receive a notification with no sound.
 * @param protectContent       Boolean Optional Protects the contents of the sent message from forwarding and saving
 * @param replyToMessageId     Integer Optional If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Boolean optional Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply Optional Additional interface options.
 *                             A JSON-serialized object for an inline keyboard, custom reply keyboard,
 *                             instructions to hide reply keyboard or to force a reply from the user.
 * @param messageThreadId        Optional Integer. Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
 */
case class SendAudio(
  chatId: ChatId,
  audio: InputFile,
  duration: Option[Int] = None,
  caption: Option[String] = None,
  parseMode: Option[ParseMode] = None,
  captionEntities: Option[List[MessageEntity]] = None,
  performer: Option[String] = None,
  title: Option[String] = None,
  thumb: Option[InputFile] = None,
  disableNotification: Option[Boolean] = None,
  protectContent: Option[Boolean] = None,
  replyToMessageId: Option[Long] = None,
  allowSendingWithoutReply: Option[Boolean] = None,
  replyMarkup: Option[ReplyMarkup] = None,
  messageThreadId: Option[Int] = None
) extends MultipartRequest[Message] {
  override def getFiles: List[(String, InputFile)] = List("audio" -> audio)
}
