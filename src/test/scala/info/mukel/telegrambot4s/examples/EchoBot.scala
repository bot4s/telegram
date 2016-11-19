package info.mukel.telegrambot4s.examples

import info.mukel.telegrambot4s._, api._, methods._, models._, Implicits._

/**
  * Echo, ohcE
  */
object EchoBot extends TestBot with Polling {
  override def onMessage(msg: Message): Unit = {
    for (text <- msg.text)
      api.request(SendMessage(msg.chat.id, text.reverse))
  }
}
