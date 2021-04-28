package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ ChatId, Message, MessageEntity, ReplyMarkup }
import com.bot4s.telegram.methods.ParseMode.ParseMode
import com.bot4s.telegram.methods.PollType.PollType

/**
 * Use this method to send a native poll.
 * A native poll can't be sent to a private chat. On success, the sent Message is returned.
 *
 * @param chatId                     Unique identifier for the target chat or username of the target channel (in the format @channelusername). A native poll can't be sent to a private chat.
 * @param question                   Poll question, 1-255 characters
 * @param options                    List of answer options, 2-10 strings 1-100 characters each
 * @param isAnonymous                Optional: if the poll needs to be anonymous, defaults to True
 * @param type                       Optional: Poll type, “quiz” or “regular”, defaults to “regular”
 * @param allowsMultipleAnswers      Optional: True, if the poll allows multiple answers, ignored for polls in quiz mode, defaults to False
 * @param correctOptionId            Optional: 0-based identifier of the correct answer option, required for polls in quiz mode
 * @param explanation                Optional: Text that is shown when a user chooses an incorrect answer or taps on the lamp icon in a quiz-style poll, 0-200 characters with at most 2 line feeds after entities parsing
 * @param explanationParseMode       Optional: String Optional Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
 * @param explanationEntities        Optional: List of special entities that appear in the poll explanation, which can be specified instead of parse_mode
 * @param openPeriod                 Optional: Amount of time in seconds the poll will be active after creation, 5-600. Can't be used together with close_date.
 * @param closeDate                  Optional: Point in time (Unix timestamp) when the poll will be automatically closed. Must be at least 5 and no more than 600 seconds in the future. Can't be used together with open_period.
 * @param isClosed                   Optional: Pass True, if the poll needs to be immediately closed. This can be useful for poll preview.
 * @param disableNotification        Sends the message silently. Users will receive a notification with no sound.
 * @param replyToMessageId           If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply   Optional: Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup                Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
 */
case class SendPoll(
  chatId: ChatId,
  question: String,
  options: Array[String],
  isAnonymous: Option[Boolean] = None,
  `type`: Option[PollType] = None,
  allowsMultipleAnswers: Option[Boolean] = None,
  correctOptionId: Option[Int] = None,
  explanation: Option[String] = None,
  explanationParseMode: Option[ParseMode] = None,
  explanationEntities: Option[Array[MessageEntity]] = None,
  openPeriod: Option[Int] = None,
  closeDate: Option[Int] = None,
  isClosed: Option[Boolean] = None,
  disableNotification: Option[Boolean] = None,
  replyToMessageId: Option[Int] = None,
  allowSendingWithoutReply: Option[Boolean] = None,
  replyMarkup: Option[ReplyMarkup] = None
) extends JsonRequest[Message]
