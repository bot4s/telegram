import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.Implicits._
import com.bot4s.telegram.marshalling._
import com.bot4s.telegram.api.declarative.{ Callbacks, Commands }
import com.bot4s.telegram.future.Polling
import com.bot4s.telegram.methods.EditMessageReplyMarkup
import com.bot4s.telegram.models.{ ChatId, InlineKeyboardButton, InlineKeyboardMarkup }

import scala.concurrent.Future
import scala.util.Try

/**
 * Show how to use callbacks, and it's shortcomings.
 *
 * @param token Bot's token.
 */
class CallbacksBot(token: String) extends ExampleBot(token) with Polling with Commands[Future] with Callbacks[Future] {

  val TAG          = "COUNTER_TAG"
  var requestCount = 0

  def markupCounter(n: Int) = {
    requestCount += 1
    InlineKeyboardMarkup.singleButton(
      InlineKeyboardButton.callbackData(s"Press me!!!\n$n - $requestCount", tag(n.toString))
    )
  }

  def tag = prefixTag(TAG) _

  onCommand("/counter") { implicit msg =>
    reply("Press to increment!", replyMarkup = markupCounter(0)).void
  }

  onCallbackWithTag(TAG) { implicit cbq =>
    // Notification only shown to the user who pressed the button.
    val ackFuture = ackCallback(cbq.from.firstName + " pressed the button!")
    // Or just ackCallback()

    val maybeEditFuture = for {
      data  <- cbq.data
      Int(n) = data
      msg   <- cbq.message
      response <- request(
                    EditMessageReplyMarkup(
                      ChatId(msg.source), // msg.chat.id
                      msg.messageId,
                      replyMarkup = markupCounter(n + 1)
                    )
                  )
    } yield response

    ackFuture.zip(maybeEditFuture.getOrElse(Future.successful(()))).void
  }

  // Extractor
  object Int {
    def unapply(s: String): Option[Int] = Try(s.toInt).toOption
  }

}
