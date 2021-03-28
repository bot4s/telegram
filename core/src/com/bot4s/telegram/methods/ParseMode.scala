package com.bot4s.telegram.methods

/** Formatting options
  *   The Bot API supports basic formatting for messages.
  *   You can use bold and italic text, as well as inline links and pre-formatted code in your bots' messages.
  *   Telegram clients will render them accordingly. You can use either markdown-style or HTML-style formatting.
  *   Note that Telegram clients will display an alert to the user before opening an inline link ('Open this link?' together with the full URL).
  *
  * Markdown style
  *   To use this mode, pass Markdown in the parse_mode field when using sendMessage. Use the following syntax in your message:
  *   *bold text*
  *   _italic text_
  *   [text](URL)
  *   `inline fixed-width code`
  *   ```pre-formatted fixed-width code block```
  * 
  * MarkdownV2 style (https://core.telegram.org/bots/api#markdownv2-style)
  *   To use this mode, pass MarkdownV2 in the parse_mode field. Use the following syntax in your message:
  * 
  *   *bold \*text*
  *   _italic \*text_
  *   __underline__
  *   ~strikethrough~
  *   *bold _italic bold ~italic bold strikethrough~ __underline italic bold___ bold*
  *   [inline URL](https://github.com/bot4s/telegram/)
  *   [inline mention of a user](tg://user?id=123456789)
  *   `inline fixed-width code`
  *   ```
  *   pre-formatted fixed-width code block
  *   ```
  *   ```python
  *   pre-formatted fixed-width code block written in the Python programming language
  *   ```
  *
  * HTML style
  *   To use this mode, pass HTML in the parse_mode field when using sendMessage. The following tags are currently supported:
  *   <b>bold</b>, <strong>bold</strong>
  *   <i>italic</i>, <em>italic</em>
  *   <a href="URL">inline URL</a>
  *   <code>inline fixed-width code</code>
  *   <pre>pre-formatted fixed-width code block</pre>
  *
  * Please note:
  *   Only the tags mentioned above are currently supported.
  *   Tags must not be nested.
  *   All <, > and & symbols that are not a part of a tag or an HTML entity must be replaced with the corresponding HTML entities (< with &lt;, > with &gt; and & with &amp;).
  *   All numerical HTML entities are supported.
  *   The API currently supports only the following named HTML entities: &lt;, &gt;, &amp; and &quot;.
  */
object ParseMode extends Enumeration {
  type ParseMode = Value
  val Markdown = Value("Markdown")
  val MarkdownV2 = Value("markdownv2")
  val HTML = Value("HTML")
}
