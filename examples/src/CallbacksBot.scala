import com.bot4s.telegram.Implicits._
import com.bot4s.telegram.api.declarative.{Callbacks, Commands}
import com.bot4s.telegram.api.Polling
import com.bot4s.telegram.methods.EditMessageReplyMarkup
import com.bot4s.telegram.models.{ChatId, InlineKeyboardButton, InlineKeyboardMarkup}

import scala.util.Try

/**
  * Show how to use callbacks, and it's shortcomings.
  *
  * @param token Bot's token.
  */
class CallbacksBot(token: String) extends ExampleBot(token)
  with Polling
  with Commands
  with Callbacks {

  val TAG = "COUNTER_TAG"
  var requestCount = 0

  def markupCounter(n: Int) = {
    requestCount += 1
    InlineKeyboardMarkup.singleButton(
      InlineKeyboardButton.callbackData(
        s"Press me!!!\n$n - $requestCount",
        tag(n.toString)))
  }

  def tag = prefixTag(TAG) _

  onCommand("/counter") { implicit msg =>
    reply("Press to increment!", replyMarkup = markupCounter(0))
  }

  onCallbackWithTag(TAG) { implicit cbq =>
    // Notification only shown to the user who pressed the button.
    ackCallback(cbq.from.firstName + " pressed the button!")
    // Or just ackCallback()

    for {
      data <- cbq.data
      Int(n) = data
      msg <- cbq.message
    } /* do */ {
      request(
        EditMessageReplyMarkup(
          ChatId(msg.source), // msg.chat.id
          msg.messageId,
          replyMarkup = markupCounter(n + 1)))
    }
  }

  // Extractor
  object Int {
    def unapply(s: String): Option[Int] = Try(s.toInt).toOption
  }

}
