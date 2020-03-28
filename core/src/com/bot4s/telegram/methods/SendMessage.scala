package com.bot4s.telegram.methods

import ParseMode.ParseMode
import com.bot4s.telegram.models.{Message, ReplyMarkup}
import com.bot4s.telegram.models.ChatId

/** Use this method to send text messages.
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
  * @param disableWebPagePreview  Boolean Optional Disables link previews for links in this message
  * @param disableNotification    Boolean Optional Sends the message silently.
  *                               iOS users will not receive a notification, Android users will receive a notification with no sound.
  * @param replyToMessageId       Integer Optional If the message is a reply, ID of the original message
  * @param replyMarkup            InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply Optional Additional interface options.
  *                               A JSON-serialized object for an inline keyboard, custom reply keyboard,
  *                               instructions to hide reply keyboard or to force a reply from the user.
  */
case class SendMessage(chatId: ChatId,
                       text: String,
                       parseMode: Option[ParseMode] = None,
                       disableWebPagePreview: Option[Boolean] = None,
                       disableNotification: Option[Boolean] = None,
                       replyToMessageId: Option[Int] = None,
                       replyMarkup: Option[ReplyMarkup] = None)
    extends JsonRequest[Message]
