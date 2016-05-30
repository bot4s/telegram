package com.github.mukel.telegrambot4s.examples

import java.net.URLEncoder

import com.github.mukel.telegrambot4s._, api._, methods._, models._, Implicits._

/**
  * Let me Google that for you!
  */
object LmgtfyBot extends TestBot with Polling with Commands {
  def lmgtfyUrl(query: Seq[String]) =
    "http://lmgtfy.com/?q=" + URLEncoder.encode(query.mkString(" "), "UTF-8")

  on("/lmgtfy") { implicit msg => args =>
    reply(lmgtfyUrl(args), disableWebPagePreview = true)
  }

  on("/lmgtfy2") { implicit msg => args =>
    val markup = InlineKeyboardMarkup(Seq(Seq(
      InlineKeyboardButton("Goto Google now!", url = lmgtfyUrl(args))
    )))
    api.request(SendMessage(msg.sender, "Let me Google that for you!", replyMarkup = markup))
  }
}
