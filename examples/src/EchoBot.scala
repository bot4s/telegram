import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.future.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models._

import scala.concurrent.Future

/**
  * Echo, ohcE
  */
class EchoBot(token: String) extends ExampleBot(token)
  with Polling {

  override def receiveMessage(msg: Message): Future[Unit] =
    msg.text.fold(Future.successful(())) { text =>
      request(SendMessage(msg.source, text.reverse)).void
    }
}
