import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.methods._
import info.mukel.telegrambot4s.models._

/**
  * Echo, ohcE
  */
class EchoBot(token: String) extends ExampleBot(token)
  with Polling {

  override def receiveMessage(msg: Message): Unit = {
    for (text <- msg.text)
      request(SendMessage(msg.source, text.reverse))
  }
}
