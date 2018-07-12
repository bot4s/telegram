import akka.http.scaladsl.model.Uri
import akka.http.scaladsl.model.Uri.Query
import com.bot4s.telegram.Implicits._
import com.bot4s.telegram.api.Polling
import com.bot4s.telegram.api.declarative.{Commands, InlineQueries}
import com.bot4s.telegram.methods.ParseMode
import com.bot4s.telegram.models._

/**
  * Let me Google that for you!
  */
class LmgtfyBot(token: String) extends ExampleBot(token)
  with Polling
  with InlineQueries
  with Commands {

  def lmgtfyBtn(query: String): InlineKeyboardMarkup = InlineKeyboardMarkup.singleButton(
    InlineKeyboardButton.url("\uD83C\uDDECoogle it now!", lmgtfyUrl(query)))

  onCommand('start | 'help) { implicit msg =>
    reply(
      s"""Generates ${"Let me \uD83C\uDDECoogle that for you!".italic} links.
         |
         |/start | /help - list commands
         |
         |/lmgtfy args - generate link
         |
         |/lmgtfy2 | /btn args - clickable button
         |
         |@Bot args - Inline mode
      """.stripMargin,
      parseMode = ParseMode.Markdown)
  }

  onCommand('lmgtfy) { implicit msg =>
    withArgs { args =>
      val query = args.mkString(" ")

      replyMd(
        query.altWithUrl(lmgtfyUrl(query)),
        disableWebPagePreview = true
      )
    }
  }

  def lmgtfyUrl(query: String): String =
    Uri("http://lmgtfy.com")
      .withQuery(Query("q" -> query))
      .toString()

  onCommand('btn | 'lmgtfy2) { implicit msg =>
    withArgs { args =>
      val query = args.mkString(" ")
      reply(query, replyMarkup = lmgtfyBtn(query))
    }
  }

  onInlineQuery { implicit iq =>
    val query = iq.query

    if (query.isEmpty)
      answerInlineQuery(Seq())
    else {

      val textMessage = InputTextMessageContent(
        query.altWithUrl(lmgtfyUrl(query)),
        disableWebPagePreview = true,
        parseMode = ParseMode.Markdown)

      val results = List(
        InlineQueryResultArticle(
          "btn:" + query,
          inputMessageContent = textMessage,
          title = iq.query,
          description = "Clickable button + link",
          replyMarkup = lmgtfyBtn(query)
        ),
        InlineQueryResultArticle(
          query,
          inputMessageContent = textMessage,
          description = "Clickable link",
          title = iq.query
        )
      )

      answerInlineQuery(results, cacheTime = 1)
    }
  }
}
