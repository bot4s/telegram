import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.future.Polling
import com.bot4s.telegram.methods._

import scala.concurrent.Future
import com.bot4s.telegram.api.declarative.Commands

class MarkdownBot(token: String) extends ExampleBot(token) with Polling with Commands[Future] {

  onCommand("markdownV2") { implicit msg =>
    reply(
      """
    *MardownV2*
*bold \*text*
_italic \*text_
__underline__
~strikethrough~
*bold _italic bold ~italic bold strikethrough~ __underline italic bold___ bold*
||spoiler||
*bold _italic bold ~italic bold strikethrough ||italic bold strikethrough spoiler||~ __underline italic bold___ bold*
[inline URL](http://www.bot4s.com/)
[inline mention of a user](tg://user?id=123456789)
`inline fixed-width code`
```
pre-formatted fixed-width code block
```
```python
pre-formatted fixed-width code block written in the Python programming language
```
    """,
      Some(ParseMode.MarkdownV2)
    ).void
  }
  onCommand("markdown") { implicit msg =>
    reply(
      """
  *Mardown parsing*
  *bold text*
_italic text_
[inline URL](http://www.bot4s.com/)
[inline mention of a user](tg://user?id=123456789)
`inline fixed-width code`
```
pre-formatted fixed-width code block
```
```python
pre-formatted fixed-width code block written in the Python programming language
```
  """,
      Some(ParseMode.Markdown)
    ).void
  }
  onCommand("html") { implicit msg =>
    reply(
      """
  <b>HTML Parser</b>
  <b>bold</b>, <strong>bold</strong>
<i>italic</i>, <em>italic</em>
<u>underline</u>, <ins>underline</ins>
<s>strikethrough</s>, <strike>strikethrough</strike>, <del>strikethrough</del>
<b>bold <i>italic bold <s>italic bold strikethrough</s> <u>underline italic bold</u></i> bold</b>
<span class="tg-spoiler">spoiler</span>
<b>bold <i>italic bold <s>italic bold strikethrough <span class="tg-spoiler">italic bold strikethrough spoiler</span></s> <u>underline italic bold</u></i> bold</b>
<a href="http://www.bot4s.com/">inline URL</a>
<a href="tg://user?id=123456789">inline mention of a user</a>
<code>inline fixed-width code</code>
<pre>pre-formatted fixed-width code block</pre>
<pre><code class="language-python">pre-formatted fixed-width code block written in the Python programming language</code></pre>
  """,
      Some(ParseMode.HTML)
    ).void
  }
}
