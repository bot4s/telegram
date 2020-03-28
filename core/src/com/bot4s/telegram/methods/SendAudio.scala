package com.bot4s.telegram.methods

import ParseMode.ParseMode
import com.bot4s.telegram.models.{Message, ReplyMarkup}
import com.bot4s.telegram.models.{ChatId, InputFile}

/** Use this method to send audio files, if you want Telegram clients to display them in the music player.
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
  * @param disableNotification  Boolean Optional Sends the message silently. iOS users will not receive a notification,
  *                             Android users will receive a notification with no sound.
  * @param replyToMessageId     Integer Optional If the message is a reply, ID of the original message
  * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply Optional Additional interface options.
  *                             A JSON-serialized object for an inline keyboard, custom reply keyboard,
  *                             instructions to hide reply keyboard or to force a reply from the user.
  */
case class SendAudio(chatId: ChatId,
                     audio: InputFile,
                     duration: Option[Int] = None,
                     caption: Option[String] = None,
                     parseMode: Option[ParseMode] = None,
                     performer: Option[String] = None,
                     title: Option[String] = None,
                     disableNotification: Option[Boolean] = None,
                     replyToMessageId: Option[Long] = None,
                     replyMarkup: Option[ReplyMarkup] = None)
    extends MultipartRequest[Message] {
  override def getFiles: List[(String, InputFile)] = List("audio" -> audio)
}
