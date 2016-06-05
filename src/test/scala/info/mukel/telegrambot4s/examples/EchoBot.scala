package info.mukel.telegrambot4s.examples

import info.mukel.telegrambot4s._, api._, methods._, models._, Implicits._

/**
  * Echo, ohcE
  */
object EchoBot extends TestBot with Polling {
  override def handleMessage(message: Message): Unit = {
    for (text <- message.text)
      api.request(SendMessage(message.chat.id, text.reverse))
  }
}
