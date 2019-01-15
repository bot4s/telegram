import cats.effect.Async
import cats.effect.Sync
import cats.syntax.functor._

import com.bot4s.telegram.cats.api.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models._

class EchoBot[F[_]: Async](token: String) extends ExampleBot[F](token) with Polling[F] {

  override implicit val syncF: Sync[F] = Async[F]

  override def receiveMessage(msg: Message): F[Unit] =
    msg.text.fold(unit) { text =>
      request(SendMessage(msg.source, text.reverse)).void
    }
}
