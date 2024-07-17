package com.bot4s.telegram.methods

import ParseMode.ParseMode
import com.bot4s.telegram.models.{ ChatId, MessageEntity, MessageId, ReplyMarkup }

/**
 * Use this method to copy messages of any kind.
 * Service messages and invoice messages can't be copied.
 * The method is analogous to the method forwardMessage, but the copied message doesn't have a link to the original message.
 * Returns the MessageId of the sent message on success.
 *
 * @param chatId               Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param fromChatId           Integer or String Unique identifier for the chat where the original message was sent (or channel username in the format @channelusername)
 * @param messageId            Integer Unique message identifier
 * @param caption              String Optional Photo caption (may also be used when resending photos by file_id), 0-200 characters
 * @param parseMode            String Optional Send Markdown or HTML, if you want Telegram apps to show bold, italic,
 *                             fixed-width text or inline URLs in the media caption.
 * @param captionEntities      A JSON-serialized list of special entities that appear in the caption, which can be specified instead of parse_mode
 * @param disableNotification  Boolean Optional Sends the message silently.
 *                             iOS users will not receive a notification, Android users will receive a notification with no sound.
 * @param protectContent       Boolean Optional Protects the contents of the sent message from forwarding and saving
 * @param replyToMessageId     Integer Optional If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Boolean optional Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply Optional Additional interface options.
 *                             A JSON-serialized object for an inline keyboard, custom reply keyboard,
 *                             instructions to hide reply keyboard or to force a reply from the user.
 * @param messageThreadId      Optional Integer. Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
 */
case class CopyMessage(
  chatId: ChatId,
  fromChatId: ChatId,
  messageId: Int,
  caption: Option[String] = None,
  parseMode: Option[ParseMode] = None,
  captionEntities: Option[List[MessageEntity]] = None,
  disableNotification: Option[Boolean] = None,
  protectContent: Option[Boolean] = None,
  replyToMessageId: Option[Int] = None,
  allowSendingWithoutReply: Option[Boolean] = None,
  replyMarkup: Option[ReplyMarkup] = None,
  messageThreadId: Option[Int] = None
) extends JsonRequest[MessageId]
