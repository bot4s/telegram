import java.util.concurrent.TimeUnit

import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.Implicits._
import com.bot4s.telegram.api.declarative.{Callbacks, Commands}
import com.bot4s.telegram.future.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models._

import scala.concurrent.{Await, Future}
import scala.util.Failure

/**
  *
  * Ported from: https://github.com/Pitasi/selfdestructbot
  */
class PollBot(token: String) extends ExampleBot(token)
  with Polling
  with Commands[Future]
  with Callbacks[Future] {

  var pollMsgId = 0

  onCommand("poll") { implicit msg =>
    val f = request(SendPoll(ChatId(msg.chat.id), "Pick A or B", Array("A", "B")))
    f.onComplete {
      case Failure(e) => println("Error " + e)
      case _ =>
    }
    for {
      poll <- f
    } yield {
      println("Poll sent")
      pollMsgId = poll.messageId
    }
  }

  onCommand("stop") { implicit msg =>
    request(StopPoll(ChatId(msg.chat.id), pollMsgId)).void
  }
}
