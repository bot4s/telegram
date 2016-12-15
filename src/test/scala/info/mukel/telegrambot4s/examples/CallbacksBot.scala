package info.mukel.telegrambot4s.examples

import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.api.{Callbacks, Commands, Polling}
import info.mukel.telegrambot4s.methods.EditMessageReplyMarkup
import info.mukel.telegrambot4s.models.{InlineKeyboardButton, InlineKeyboardMarkup}

import scala.util.Try

/**
  * Show how to use callbacks, and it's shortcomings.
  * @param token Bot's token.
  */
class CallbacksBot(token: String) extends TestBot(token) with Polling with Commands with Callbacks {
  val TAG = "COUNTER_TAG"

  var requestCount = 0

  def markupCounter(n: Int) = {
    requestCount += 1
    InlineKeyboardMarkup(Seq(Seq(InlineKeyboardButton(s"Press me!!!\n$n - $requestCount",
      callbackData = s"$TAG$n"))))
  }

  on("/counter") { implicit msg => _ =>
    reply("Press to increment!", replyMarkup = markupCounter(0))
  }

  onCallbackWithTag(TAG) { implicit cbq =>
    // Notification only shown to the user who pressed the button.
    ackCallback(cbq.from.firstName + ", you pressed the button!")

    for {
      data <- cbq.data.map(_.stripPrefix(TAG))
      msg <- cbq.message
      n <- Try(data.toInt).toOption
    } /* do */ {
      request(EditMessageReplyMarkup(
        msg.chat.id,
        msg.messageId,
        replyMarkup = markupCounter(n + 1)))
    }
  }
}
