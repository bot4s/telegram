package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ Message, ReplyMarkup }
import com.bot4s.telegram.models.{ ChatId, InputFile }

/**
 * Use this method to send static .WEBP or animated .TGS stickers. On success, the sent Message is returned.
 *
 * @param chatId               Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param sticker              InputFile or String Sticker to send.
 *                             Pass a file_id as String to send a file that exists on the Telegram servers (recommended),
 *                             pass an HTTP URL as a String for Telegram to get a .webp file from the Internet, or upload a new one using multipart/form-data.
 * @param protectContent       Boolean Optional Protects the contents of the sent message from forwarding and saving
 * @param disableNotification  Boolean Optional Sends the message silently.
 *                             iOS users will not receive a notification, Android users will receive a notification with no sound.
 * @param replyToMessageId     Integer Optional If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Boolean optional Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply Optional Additional interface options.
 *                             A JSON-serialized object for an inline keyboard, custom reply keyboard,
 *                             instructions to hide reply keyboard or to force a reply from the user.
 */
case class SendSticker(
  chatId: ChatId,
  sticker: InputFile,
  disableNotification: Option[Boolean] = None,
  protectContent: Option[Boolean] = None,
  replyToMessageId: Option[Int] = None,
  allowSendingWithoutReply: Option[Boolean] = None,
  replyMarkup: Option[ReplyMarkup] = None
) extends MultipartRequest[Message] {
  override def getFiles: List[(String, InputFile)] = List("sticker" -> sticker)
}
