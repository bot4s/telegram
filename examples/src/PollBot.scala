import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.Implicits._
import com.bot4s.telegram.api.declarative.{Callbacks, Commands}
import com.bot4s.telegram.future.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models._

import scala.concurrent.Future
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

  def sendPoll(poll: SendPoll) = {
    val f = request(poll)
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

  onCommand("poll") { implicit msg =>
    val f = SendPoll(ChatId(msg.chat.id), "Pick A or B", Array("A", "B"))
    sendPoll(f)
  }

  onCommand("quizPoll") { implicit msg => 
    val f = SendPoll(
      chatId = ChatId(msg.chat.id),
      question =  "Pick A or B",
      options = Array("A", "B"),
      `type`  = PollType.quiz,
      correctOptionId = Some(0),
      explanation = "The correct answer was A",
      explanationParseMode = ParseMode.Markdown
    )
    sendPoll(f)
  }

  onCommand("stop") { implicit msg =>
    request(StopPoll(ChatId(msg.chat.id), pollMsgId)).void
  }
}
