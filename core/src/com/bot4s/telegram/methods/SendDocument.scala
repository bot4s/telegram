package com.bot4s.telegram.methods

import ParseMode.ParseMode
import com.bot4s.telegram.models.{Message, ReplyMarkup}
import com.bot4s.telegram.models.{ChatId, InputFile}

/** Use this method to send general files. On success, the sent Message is returned.
  * Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
  *
  * @param chatId               Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param document             InputFile or String File to send.
  *                             Pass a file_id as String to send a file that exists on the Telegram servers (recommended),
  *                             pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data.
  * @param caption              String Optional Document caption (may also be used when resending documents by file_id), 0-200 characters
  * @param parseMode            String Optional Send Markdown or HTML, if you want Telegram apps to show bold, italic,
  *                             fixed-width text or inline URLs in the media caption.
  * @param disableNotification  Boolean Optional Sends the message silently. iOS users will not receive a notification,
  *                             Android users will receive a notification with no sound.
  * @param replyToMessageId     Integer Optional If the message is a reply, ID of the original message
  * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply Optional Additional interface options.
  *                             A JSON-serialized object for an inline keyboard, custom reply keyboard,
  *                             instructions to hide reply keyboard or to force a reply from the user.
  */
case class SendDocument(chatId: ChatId,
                        document: InputFile,
                        caption: Option[String] = None,
                        parseMode: Option[ParseMode] = None,
                        disableNotification: Option[Boolean] = None,
                        replyToMessageId: Option[Long] = None,
                        replyMarkup: Option[ReplyMarkup] = None)
    extends MultipartRequest[Message] {
  override def getFiles: List[(String, InputFile)] =
    List("document" -> document)
}
