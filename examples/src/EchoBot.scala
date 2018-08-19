import com.bot4s.telegram.api.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models._

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
