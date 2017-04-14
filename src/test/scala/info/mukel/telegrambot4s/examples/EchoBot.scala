package info.mukel.telegrambot4s.examples

import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.methods._
import info.mukel.telegrambot4s.models._

/**
  * Echo, ohcE
  */
class EchoBot(token: String) extends TestBot(token) with Polling {
  override def onMessage(msg: Message): Unit = {
    for (text <- msg.text)
      request(SendMessage(msg.source, text.reverse))
  }
}
