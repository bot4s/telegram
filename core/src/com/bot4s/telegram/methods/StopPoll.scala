package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ChatId, Poll, ReplyMarkup}

/**
  * Use this method to stop a poll which was sent by the bot.
  * On success, the stopped Poll with the final results is returned.
  *
  * @param chatId       Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param messageId    Identifier of the original message with the poll
  * @param replyMarkup  A JSON-serialized object for a new message inline keyboard.
  */
case class StopPoll(chatId: ChatId,
                    messageId: Option[Int] = None,
                    replyMarkup: Option[ReplyMarkup] = None)
    extends JsonRequest[Poll]
