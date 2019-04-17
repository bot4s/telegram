package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ChatId, Message, ReplyMarkup}

/**
  * Use this method to send a native poll.
  * A native poll can't be sent to a private chat. On success, the sent Message is returned.
  *
  * @param chatId               Unique identifier for the target chat or username of the target channel (in the format @channelusername). A native poll can't be sent to a private chat.
  * @param question             Poll question, 1-255 characters
  * @param options              List of answer options, 2-10 strings 1-100 characters each
  * @param disableNotification  Sends the message silently. Users will receive a notification with no sound.
  * @param replyToMessageId     If the message is a reply, ID of the original message
  * @param replyMarkup          Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
  */
case class SendPoll(chatId              : ChatId,
                    question            : String,
                    options             : Array[String],
                    disableNotification : Option[Boolean] = None,
                    replyToMessageId    : Option[Int] = None,
                    replyMarkup         : Option[ReplyMarkup] = None
                   ) extends JsonRequest[Message]
