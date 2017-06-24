package info.mukel.telegrambot4s.examples

import java.net.URLEncoder

import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.api.declarative.{Commands, Messages}
import info.mukel.telegrambot4s.models._
import info.mukel.telegrambot4s.Implicits._

/**
  * Let me Google that for you!
  */
class LmgtfyBot(token: String) extends ExampleBot(token)
  with Polling
  with Messages
  with Commands {

  def lmgtfyUrl(query: Seq[String]) =
    "http://lmgtfy.com/?q=" + URLEncoder.encode(query.mkString(" "), "UTF-8")

  onCommand("/lmgtfy") { implicit msg =>
    withArgs { args =>
      reply(lmgtfyUrl(args), disableWebPagePreview = true)
    }
  }

  onCommand("/lmgtfy2") { implicit msg =>
    withArgs { args =>
      val singleButtonMarkup = InlineKeyboardMarkup(
        InlineKeyboardButton.url("Google it now!", lmgtfyUrl(args)))

      reply("Let me Google that for you!",
        replyMarkup = singleButtonMarkup)
    }
  }
}
