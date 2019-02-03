import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.future.{Polling, Chats}

import scala.concurrent.Future

class ChatBot(token: String) extends ExampleBot(token)
  with Polling
  with Commands[Future]
  with Chats {

  onCommand("/hello") { implicit msg =>
    val chat = createChat(msg.source)
    for {
      _ <- chat.write("Enter parameter #1")
      param1 <- chat.read()
      _ <- chat.write("Enter parameter #2")
      param2 <- chat.read()
      _ <- chat.write(s"You just entered /hello ${param1.text} ${param2.text}")
    } yield ()
  }
}
