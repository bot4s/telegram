import java.util.{Timer, TimerTask}

import info.mukel.telegrambot4s.akka.api.AkkaDefaults
import info.mukel.telegrambot4s.api.TelegramBot

import scala.concurrent.duration.Duration
import scala.concurrent.{Future, Promise}
import scala.util.Try

/** Quick helper to spawn example bots.
  *
  * Mix Polling or Webhook accordingly.
  *
  * Example:
  * new EchoBot("123456789:qwertyuiopasdfghjklyxcvbnm123456789").run()
  *
  * @param token Bot's token.
  */
abstract class ExampleBot(val token: String) extends TelegramBot {

  def after[T](duration: Duration)(block: => T): Future[T] = {
    val promise = Promise[T]()
    val t = new Timer()
    t.schedule(new TimerTask {
      override def run(): Unit = {
        promise.complete(Try(block))
      }
    }, duration.toMillis)
    promise.future
  }

}

abstract class AkkaExampleBot(val token: String) extends TelegramBot with AkkaDefaults {

  def after[T](duration: Duration)(block: => T): Future[T] = {
    val promise = Promise[T]()
    val t = new Timer()
    t.schedule(new TimerTask {
      override def run(): Unit = {
        promise.complete(Try(block))
      }
    }, duration.toMillis)
    promise.future
  }

}

