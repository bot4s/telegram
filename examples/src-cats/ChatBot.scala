import cats.effect.Async
import cats.effect.Concurrent
import cats.effect.concurrent.Deferred
import cats.effect.syntax.concurrent._
import cats.syntax.flatMap._
import cats.syntax.functor._

import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.api.declarative.{Deferred => TelegramDeferred}
import com.bot4s.telegram.api.declarative.Chats
import com.bot4s.telegram.cats.Polling

object ChatBot {

  implicit def dfd2telegram[F[_], A](dfd: Deferred[F, A]): TelegramDeferred[F, A] = new TelegramDeferred[F, A] {
    def complete(a: A) = dfd.complete(a)

    def get: F[A] = dfd.get
  }
}

class ChatBot[F[_]: Async: Concurrent](token: String) extends ExampleBot[F](token)
    with Polling[F]
    with Commands[F]
    with Chats[F] {
  import ChatBot._

  override def delay[A](thunk: => A): F[A] = Async[F].delay(thunk)

  override def makeDeferred[A] = Deferred[F, A].map(dfd2telegram)

  onCommand("/hello") { implicit msg =>
    val chat = createChat(msg.source)
    (for {
      _ <- chat.write("Enter parameter #1")
      param1 <- chat.read()
      _ <- chat.write("Enter parameter #2")
      param2 <- chat.read()
      _ <- chat.write(s"You just entered /hello ${param1.text} ${param2.text}")
    } yield ()).start.void
  }

}
