package info.mukel.telegrambot4s.examples

import info.mukel.telegrambot4s.api.{Commands, Polling}
import info.mukel.telegrambot4s.models.Message

/**
  * Per-chat counter.
  * @param token Bot's token.
  */
class StatefulBot(token: String) extends ExampleBot(token) with Polling with Commands with PerChatState[Int] {
  on("/inc", "shows and increment chat counter") { implicit msg => _ =>
    withChatState { s =>
      val n = s.getOrElse(0)
      setChatState(n+1)
      reply(s"Counter: $n")
    }
  }
}

/**
  * Simple extension for having stateful Telegram Bots (per chat).
  * The main issue is locking/synchronization, actors (FSM) are a better alternative.
  * This can be easily adapted to handle per-user or per-user+chat state.
  */
trait PerChatState[S] {
  private val chatState = collection.mutable.Map[Long, S]()
  private def atomic[T](f: => T): T = chatState.synchronized { f }

  def getChatState(implicit msg: Message): Option[S] = atomic { chatState.get(msg.chat.id) }
  def setChatState(value: S)(implicit msg: Message): Unit = atomic { chatState.update(msg.chat.id, value) }
  def clearChatState(implicit msg: Message): Unit = atomic { chatState.remove(msg.chat.id) }

  def withChatState(f: Option[S] => Unit)(implicit msg: Message): Unit = f(getChatState)
}
