package info.mukel.telegram.bots.v2

import java.net.URLEncoder

import info.mukel.telegram.bots.v2.methods.{AnswerCallbackQuery, SendMessage}
import info.mukel.telegram.bots.v2.model._
import info.mukel.telegram.bots.v2.api.Implicits._
import info.mukel.telegram.bots.v2.api.TelegramApiAkka

/**
  * Created by mukel on 5/7/16.
  */
object TestBot extends TelegramBot with Polling with Commands {
  val token = scala.io.Source.fromFile("./tokens/flunkey_bot.token").getLines().next
  override val api =  new TelegramApiAkka(token)

  on("lmgtfy") { implicit message => args =>
      reply(
        "http://lmgtfy.com/?q=" + URLEncoder.encode(args mkString " ", "UTF-8"),
        disableWebPagePreview = true
      )
  }

  on("markup") { implicit message => _ /* ignore args */ =>
    val button1 = InlineKeyboardButton("Sucks!", callbackData = "button1")
    val button2 = InlineKeyboardButton("I'm a fan!", callbackData = "button2")
    val kb = Array(Array(button1, button2))
    val markup = InlineKeyboardMarkup(kb)

    api.request(SendMessage(message.chat.id, "Bieber?", replyMarkup = markup))
  }

  override def handleCallbackQuery(callbackQuery: CallbackQuery): Unit = {
    api.request(SendMessage(callbackQuery.from.id, "Callback response: " + callbackQuery.data.getOrElse("Nothing?")))

    // Acknowledge callback (the web client restarts if not)
    api.request(AnswerCallbackQuery(callbackQuery.id, "This will be shown on top"))
  }
}

object Pepe extends App {
  TestBot.run()
}