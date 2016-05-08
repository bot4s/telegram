package info.mukel.telegram.bots.v2

import info.mukel.telegram.bots.v2.methods.SendMessage
import info.mukel.telegram.bots.v2.model.Update
import info.mukel.telegram.bots.v2.api.Implicits._
import info.mukel.telegram.bots.v2.api.TelegramApiAkka

/**
  * Created by mukel on 5/7/16.
  */
object EchoBot extends TelegramBot with Polling {
  val token = scala.io.Source.fromFile("./tokens/flunkey_bot.token").getLines().next

  override val api =  new TelegramApiAkka(token)

  override def handleUpdate(update: Update): Unit = {
    update.message.foreach {
      msg =>
        api.request(SendMessage(msg.chat.id, msg.text.getOrElse("EMPTY")))
    }
  }
}

object Main extends App {
  EchoBot.run()
}
