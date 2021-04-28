import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.future.Polling
import com.bot4s.telegram.models.Message

import scala.concurrent.Future

/**
 * Simple extension for having stateful Telegram Bots (per chat).
 * The main issue is locking/synchronization, actors (FSM) are a better alternative.
 * This can be easily adapted to handle per-user or per-user+chat state.
 */
trait PerChatState[S] {
  private val chatState = collection.mutable.Map[Long, S]()

  def setChatState(value: S)(implicit msg: Message): Unit = atomic {
    chatState.update(msg.chat.id, value)
  }

  def clearChatState(implicit msg: Message): Unit = atomic {
    chatState.remove(msg.chat.id)
  }

  private def atomic[T](f: => T): T = chatState.synchronized {
    f
  }

  def withChatState(f: Option[S] => Future[Unit])(implicit msg: Message) = f(getChatState)

  def getChatState(implicit msg: Message): Option[S] = atomic {
    chatState.get(msg.chat.id)
  }
}

/**
 * Per-chat counter.
 *
 * @param token Bot's token.
 */
class StatefulBot(token: String) extends ExampleBot(token) with Polling with Commands[Future] with PerChatState[Int] {

  onCommand("/inc") { implicit msg =>
    withChatState { s =>
      val n = s.getOrElse(0)
      setChatState(n + 1)
      reply(s"Counter: $n").void
    }
  }
}
