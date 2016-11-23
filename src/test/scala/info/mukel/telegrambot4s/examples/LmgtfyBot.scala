package info.mukel.telegrambot4s.examples

import java.net.URLEncoder

import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.methods._
import info.mukel.telegrambot4s.models._

/**
  * Let me Google that for you!
  */
class LmgtfyBot(token: String) extends TestBot(token) with Polling with Commands {
  def lmgtfyUrl(query: Seq[String]) =
    "http://lmgtfy.com/?q=" + URLEncoder.encode(query.mkString(" "), "UTF-8")

  on("/lmgtfy") { implicit msg => args =>
    reply(lmgtfyUrl(args), disableWebPagePreview = true)
  }

  on("/lmgtfy2") { implicit msg => args =>
    val markup = InlineKeyboardMarkup(Seq(Seq(
      InlineKeyboardButton("Goto Google now!", url = lmgtfyUrl(args))
    )))
    request(SendMessage(msg.sender, "Let me Google that for you!", replyMarkup = markup))
  }
}
