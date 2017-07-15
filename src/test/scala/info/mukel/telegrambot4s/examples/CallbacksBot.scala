package info.mukel.telegrambot4s.examples

import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.api.declarative.{Callbacks, Commands}
import info.mukel.telegrambot4s.api.{Extractors, Polling}
import info.mukel.telegrambot4s.methods.EditMessageReplyMarkup
import info.mukel.telegrambot4s.models.{InlineKeyboardButton, InlineKeyboardMarkup}

/**
  * Show how to use callbacks, and it's shortcomings.
  * @param token Bot's token.
  */
class CallbacksBot(token: String) extends ExampleBot(token)
  with Polling
  with Commands
  with Callbacks {

  val TAG = "COUNTER_TAG"

  def tag = prefixTag(TAG) _

  var requestCount = 0

  def markupCounter(n: Int) = {
    requestCount += 1
    InlineKeyboardMarkup(
      InlineKeyboardButton.callbackData(
        s"Press me!!!\n$n - $requestCount",
        tag(n.toString)))
  }

  onCommand("/counter") { implicit msg =>
    reply("Press to increment!", replyMarkup = markupCounter(0))
  }

  onCallbackWithTag(TAG) { implicit cbq =>
    // Notification only shown to the user who pressed the button.
    ackCallback(cbq.from.firstName + " pressed the button!")
    // Or just ackCallback()

    for {
      data <- cbq.data
      Extractors.Int(n) = data
      msg <- cbq.message
    } /* do */ {
      request(
        EditMessageReplyMarkup(
          msg.source, // msg.chat.id
          msg.messageId,
          replyMarkup = markupCounter(n + 1)))
    }
  }
}
