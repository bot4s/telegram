import zio.Task

import com.bot4s.telegram.cats.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models._
import sttp.client4.Backend
import com.bot4s.telegram.cats.TelegramBot
import zio.interop.catz._

class EchoBot(token: String, backend: Backend[Task]) extends TelegramBot(token, backend) with Polling[Task] {

  override def receiveMessage(msg: Message): Task[Unit] =
    msg.text.fold(unit) { text =>
      request(SendMessage(msg.source, text.reverse)).ignore
    }
}
