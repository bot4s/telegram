package com.bot4s.telegram.methods

import ParseMode.ParseMode
import com.bot4s.telegram.models.{ Message, MessageEntity, ReplyMarkup }
import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to send text messages.
 * On success, the sent Message is returned.
 *
 * ==Formatting options==
 *   The Bot API supports basic formatting for messages. You can use bold and italic text, as well as inline links and pre-formatted code in your bots' messages.
 *   Telegram clients will render them accordingly. You can use either markdown-style or HTML-style formatting.
 *   Note that Telegram clients will display an alert to the user before opening an inline link ('Open this link?' together with the full URL).
 *
 * ===Markdown style===
 *   To use this mode, pass Markdown in the parse_mode field when using sendMessage. Use the following syntax in your message:
 *   *bold text*
 *   _italic text_
 *   [text](URL)
 *   `inline fixed-width code`
 *   ```pre-formatted fixed-width code block```
 *
 * ===HTML style===
 *   To use this mode, pass HTML in the parse_mode field when using sendMessage. The following tags are currently supported:
 *   <b>bold</b>, <strong>bold</strong>
 *   <i>italic</i>, <em>italic</em>
 *   <a href="URL">inline URL</a>
 *   <code>inline fixed-width code</code>
 *   <pre>pre-formatted fixed-width code block</pre>
 *
 * '''Please note:'''
 *
 *   Only the tags mentioned above are currently supported.
 *   Tags must not be nested.
 *   All <, > and & symbols that are not a part of a tag or an HTML entity must be replaced with the corresponding HTML entities (< with &lt;, > with &gt; and & with &amp;).
 *   All numerical HTML entities are supported.
 *   The API currently supports only the following named HTML entities: &lt;, &gt;, &amp; and &quot;.
 *
 * @param chatId                 Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param text                   String Text of the message to be sent
 * @param parseMode              String Optional Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
 * @param entities               Optional list A JSON-serialized list of special entities that appear in message text, which can be specified instead of parse_mode
 * @param disableWebPagePreview  Boolean Optional Disables link previews for links in this message
 * @param protectContent         Boolean Optional Protects the contents of the sent message from forwarding and saving
 * @param disableNotification    Boolean Optional Sends the message silently.
 *                               iOS users will not receive a notification, Android users will receive a notification with no sound.
 * @param replyToMessageId       Integer Optional If the message is a reply, ID of the original message
 * @param allowSendingWithoutReply Boolean optional Pass True, if the message should be sent even if the specified replied-to message is not found
 * @param replyMarkup            InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply Optional Additional interface options.
 *                               A JSON-serialized object for an inline keyboard, custom reply keyboard,
 *                               instructions to hide reply keyboard or to force a reply from the user.
 * @param messageThreadId        Optional Integer. Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
 */
case class SendMessage(
  chatId: ChatId,
  text: String,
  parseMode: Option[ParseMode] = None,
  entities: Option[List[MessageEntity]] = None,
  disableWebPagePreview: Option[Boolean] = None,
  disableNotification: Option[Boolean] = None,
  protectContent: Option[Boolean] = None,
  replyToMessageId: Option[Int] = None,
  allowSendingWithoutReply: Option[Boolean] = None,
  replyMarkup: Option[ReplyMarkup] = None,
  messageThreadId: Option[Int] = None
) extends JsonRequest[Message]

object SendMessage {
  implicit val customConfig: Configuration        = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[SendMessage] = deriveConfiguredEncoder
}
