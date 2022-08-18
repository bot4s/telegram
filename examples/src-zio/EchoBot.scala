import zio.Task

import com.bot4s.telegram.cats.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models._

class EchoBot(token: String) extends ExampleBot(token) with Polling[Task] {

  override def receiveMessage(msg: Message): Task[Unit] =
    msg.text.fold(unit) { text =>
      request(SendMessage(msg.source, text.reverse)).ignore
    }
}
