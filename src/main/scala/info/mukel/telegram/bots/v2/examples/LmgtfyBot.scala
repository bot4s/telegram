package info.mukel.telegram.bots.v2.examples

import java.net.URLEncoder

import info.mukel.telegram.bots.v2.api.Implicits._
import info.mukel.telegram.bots.v2.methods.SendMessage
import info.mukel.telegram.bots.v2.model._
import info.mukel.telegram.bots.v2.{Commands, Polling}

/**
  * Created by mukel on 5/9/16.
  */
object LmgtfyBot extends TestBot with Polling with Commands {

  def lmgtfyUrl(query: Seq[String]) =
    "http://lmgtfy.com/?q=" + URLEncoder.encode(query.mkString(" "), "UTF-8")

  on("lmgtfy") { implicit message => args =>
    reply(
      lmgtfyUrl(args),
      disableWebPagePreview = true
    )
  }

  on("lmgtfy2") { implicit message => args =>
    val markup = InlineKeyboardMarkup(Seq(Seq(
      InlineKeyboardButton("Goto Google now!", url = lmgtfyUrl(args))
    )))
    api.request(SendMessage(message.sender, "Let me Google that for you!", replyMarkup = markup))
  }
}
