package info.mukel.telegrambot4s.methods

import info.mukel.telegrambot4s.models.{InputFile, Message, ReplyMarkup}

/** Use this method to send audio files, if you want Telegram clients to display the file as a playable voice message. For this to work, your audio must be in an .ogg file encoded with OPUS (other formats may be sent as Audio or Document). On success, the sent Message is returned. Bots can currently send voice messages of up to 50 MB in size, this limit may be changed in the future.
  *
  * @param chatId               Integer or String	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param voice                InputFile or String	Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new audio file using multipart/form-data.
  * @param duration             Integer	Optional	Duration of sent audio in seconds
  * @param disableNotification  Boolean	Optional	Sends the message silently. iOS users will not receive a notification, Android users will receive a notification with no sound.
  * @param replyToMessageId     Integer	Optional	If the message is a reply, ID of the original message
  * @param replyMarkup          InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to hide reply keyboard or to force a reply from the user.
  */
case class SendVoice(
                      chatId              : Either[Long, String],
                      voice               : Either[InputFile, String],
                      duration            : Option[String] = None,
                      disableNotification : Option[Boolean] = None,
                      replyToMessageId    : Option[Long] = None,
                      replyMarkup         : Option[ReplyMarkup] = None
                    ) extends ApiRequestMultipart[Message]
