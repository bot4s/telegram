import cats.syntax.functor._
import com.bot4s.telegram.future.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models._

import scala.concurrent.Future

class Api67Bot(token: String) extends ExampleBot(token) with Polling {
  override def receiveMessage(msg: Message): Future[Unit] =
    msg.text match {
      case Some("/get_my_name") =>
        request(GetMyName()).flatMap(name => request(SendMessage(msg.chat.id, name.name))).void
      case Some("/set_my_name") => request(SetMyName(Some("bot4s_telegram_test_bot"))).void
      case _                    => Future.successful(())
    }

}
