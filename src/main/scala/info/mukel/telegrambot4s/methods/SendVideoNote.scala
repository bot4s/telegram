package info.mukel.telegrambot4s.methods

import info.mukel.telegrambot4s.models.{InputFile, Message, ReplyMarkup}

/**
  * As of v.4.0, Telegram clients support rounded square mp4 videos of up to 1 minute long.
  * Use this method to send video messages.
  * On success, the sent Message is returned.
  *
  * @param chatId               Integer or String Yes Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param videoNote            InputFile or String Yes Video note to send. Pass a file_id as String to send a video note that exists on the Telegram servers (recommended) or upload a new video using multipart/form-data. More info on Sending Files ». Sending video notes by a URL is currently unsupported
  * @param duration             Integer Optional Duration of sent video in seconds
  * @param length               Integer Optional Video width and height
  * @param disableNotification  Boolean Optional Sends the message silently. iOS users will not receive a notification, Android users will receive a notification with no sound.
  * @param replyToMessageId     Integer Optional If the message is a reply, ID of the original message
  * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply Optional
  */
case class SendVideoNote(
                          chatId              : Long Either String,
                          videoNote           : InputFile Either String,
                          duration            : Option[Int] = None,
                          length              : Option[Int] = None,
                          disableNotification : Option[Boolean] = None,
                          replyToMessageId    : Option[Long] = None,
                          replyMarkup         : Option[ReplyMarkup] = None
                        ) extends ApiRequestMultipart[Message]
