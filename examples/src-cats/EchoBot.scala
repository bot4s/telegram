import cats.effect.{ Concurrent, ContextShift }
import cats.syntax.functor._

import com.bot4s.telegram.cats.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models._

class EchoBot[F[_]: Concurrent: ContextShift](token: String) extends ExampleBot[F](token) with Polling[F] {

  override def receiveMessage(msg: Message): F[Unit] =
    msg.text.fold(unit) { text =>
      request(SendMessage(msg.source, text.reverse)).void
    }
}
