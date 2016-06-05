package info.mukel.telegrambot4s.examples

import com.github.mukel.telegrambot4s._
import info.mukel.telegrambot4s.Implicits
import info.mukel.telegrambot4s.api.Polling
import info.mukel.telegrambot4s.methods.SendMessage
import info.mukel.telegrambot4s.models.Message

/**
  * Echo, ohcE
  */
object EchoBot extends TestBot with Polling {
  override def handleMessage(message: Message): Unit = {
    for (text <- message.text)
      api.request(SendMessage(message.chat.id, text.reverse))
  }
}
