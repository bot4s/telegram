package com.github.mukel.telegrambot4s.methods

import com.github.mukel.telegrambot4s.models.{InputFile, Message, ReplyMarkup}

/** Use this method to send audio files, if you want Telegram clients to display them in the music player.
  * Your audio must be in the .mp3 format. On success, the sent Message is returned.
  * Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
  *
  * For sending voice messages, use the sendVoice method instead.
  *
  * @param chatId               Integer or String	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param audio                InputFile or String	Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new audio file using multipart/form-data.
  * @param duration             Integer	Optional	Duration of the audio in seconds
  * @param performer            String	Optional	Performer
  * @param title                String	Optional	Track name
  * @param disableNotification  Boolean	Optional	Sends the message silently. iOS users will not receive a notification, Android users will receive a notification with no sound.
  * @param replyToMessageId     Integer	Optional	If the message is a reply, ID of the original message
  * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to hide reply keyboard or to force a reply from the user.
  */
case class SendAudio(
                    chatId               : Either[Long, String],
                    audio                : Either[InputFile, String],
                    duration             : Option[Int] = None,
                    performer            : Option[String] = None,
                    title                : Option[String] = None,
                    disableNotification  : Option[Boolean] = None,
                    replyToMessageId     : Option[Long] = None,
                    replyMarkup          : Option[ReplyMarkup] = None
                    ) extends ApiRequestMultipart[Message]
