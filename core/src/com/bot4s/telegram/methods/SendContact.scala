package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ Message, ReplyMarkup }
import com.bot4s.telegram.models.ChatId

/**
 * Use this method to send phone contacts.
 * On success, the sent Message is returned.
 *
 * @param chatId               Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param phoneNumber          String Contact's phone number
 * @param firstName            String Contact's first name
 * @param lastName             String Optional Contact's last name
 * @param vcard                String Optional Additional data about the contact in the form of a vCard, 0-2048 bytes
 * @param disableNotification  Boolean Optional Sends the message silently.
 *                             iOS users will not receive a notification, Android users will receive a notification with no sound.
 * @param protectContent       Boolean Optional Protects the contents of the sent message from forwarding and saving
 * @param replyToMessageId     Integer Optional If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Boolean optional Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply Optional Additional interface options.
 *                             A JSON-serialized object for an inline keyboard, custom reply keyboard,
 *                             instructions to hide keyboard or to force a reply from the user.
 * @param messageThreadId        Optional Integer. Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
 */
case class SendContact(
  chatId: ChatId,
  phoneNumber: String,
  firstName: String,
  lastName: Option[String] = None,
  vcard: Option[String] = None,
  protectContent: Option[Boolean] = None,
  disableNotification: Option[Boolean] = None,
  replyToMessageId: Option[Int] = None,
  allowSendingWithoutReply: Option[Boolean] = None,
  replyMarkup: Option[ReplyMarkup] = None,
  messageThreadId: Option[Int] = None
) extends JsonRequest[Message]
