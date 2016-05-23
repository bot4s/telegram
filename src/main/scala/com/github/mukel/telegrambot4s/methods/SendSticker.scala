package com.github.mukel.telegrambot4s.methods

import com.github.mukel.telegrambot4s.models.{InputFile, Message, ReplyMarkup}

/** sendSticker
  *
  * Use this method to send .webp stickers. On success, the sent Message is returned.
  *
  * @param chatId              Integer or String	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param sticker              InputFile or String	Sticker to send. You can either pass a file_id as String to resend a sticker that is already on the Telegram servers, or upload a new sticker using multipart/form-data.
  * @param disableNotification  Boolean	Optional	Sends the message silently. iOS users will not receive a notification, Android users will receive a notification with no sound.
  * @param replyToMessageId     Integer	Optional	If the message is a reply, ID of the original message
  * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to hide reply keyboard or to force a reply from the user.
  */
case class SendSticker(
                        chatId              : Either[Long, String],
                        sticker             : Either[InputFile, String],
                        disableNotification : Option[Boolean] = None,
                        replyToMessageId    : Option[Long] = None,
                        replyMarkup         : Option[ReplyMarkup] = None
                      ) extends ApiRequestMultipart[Message]
