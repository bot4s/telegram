package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ Message, ReplyMarkup }
import com.bot4s.telegram.models.{ ChatId, InputFile }

/**
 * As of v.4.0, Telegram clients support rounded square mp4 videos of up to 1 minute long.
 * Use this method to send video messages.
 * On success, the sent Message is returned.
 *
 * @param chatId               Integer or String Yes Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param videoNote            InputFile or String Yes Video note to send.
 *                             Pass a file_id as String to send a video note that exists on the Telegram servers (recommended)
 *                             or upload a new video using multipart/form-data. More info on Sending Files ».
 *                             Sending video notes by a URL is currently unsupported
 * @param duration             Integer Optional Duration of sent video in seconds
 * @param length               Integer Optional Video width and height
 * @param disableNotification  Boolean Optional Sends the message silently.
 *                             iOS users will not receive a notification, Android users will receive a notification with no sound.
 * @param protectContent       Boolean Optional Protects the contents of the sent message from forwarding and saving
 * @param replyToMessageId     Integer Optional If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Boolean optional Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply Optional
 * @param messageThreadId        Optional Integer. Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
 */
case class SendVideoNote(
  chatId: ChatId,
  videoNote: InputFile,
  duration: Option[Int] = None,
  length: Option[Int] = None,
  thumb: Option[InputFile] = None,
  disableNotification: Option[Boolean] = None,
  protectContent: Option[Boolean] = None,
  replyToMessageId: Option[Int] = None,
  allowSendingWithoutReply: Option[Boolean] = None,
  replyMarkup: Option[ReplyMarkup] = None,
  messageThreadId: Option[Int] = None
) extends MultipartRequest[Message] {
  override def getFiles: List[(String, InputFile)] = List("videoNote" -> videoNote)
}
