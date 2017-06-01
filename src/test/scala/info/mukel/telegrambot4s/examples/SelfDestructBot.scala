package info.mukel.telegrambot4s.examples

import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.marshalling.HttpMarshalling
import info.mukel.telegrambot4s.methods._
import info.mukel.telegrambot4s.models._
import scala.concurrent.duration._

/**
  * Send self-destructing messages.
  * Ported from: https://github.com/Pitasi/selfdestructbot
  */
class SelfDestructBot(token: String) extends ExampleBot(token) with Polling with Callbacks {

  import UpdateType.Filters._
  override def allowedUpdates = InlineUpdates ++ CallbackUpdates

  override def onCallbackQuery(callbackQuery: CallbackQuery): Unit = {
    super.onCallbackQuery(callbackQuery)

    request(AnswerCallbackQuery(callbackQuery.id,
      s"X seconds remaining."))
  }

  override def onChosenInlineResult(chosenInlineResult: ChosenInlineResult): Unit = {
    super.onChosenInlineResult(chosenInlineResult)
    system.scheduler.scheduleOnce(chosenInlineResult.resultId.toInt.seconds) {
      request(EditMessageText(
        text = "[ expired ]",
        inlineMessageId = chosenInlineResult.inlineMessageId))
    }
  }

  override def onInlineQuery(inlineQuery: InlineQuery): Unit = {
    super.onInlineQuery(inlineQuery)

    def buildResult(timeout: Int, msg: String): InlineQueryResult = {
      val btn = InlineKeyboardButton.callbackData("ℹ️️ Info", s"$timeout")
      InlineQueryResultArticle(
        timeout.toString,
        s"$timeout seconds",
        inputMessageContent = InputTextMessageContent(msg),
        description = s"Message will be deleted in $timeout seconds",
        replyMarkup = InlineKeyboardMarkup(btn))
    }

    val results = Seq(3, 5, 10, 30) map (buildResult(_, inlineQuery.query))
    println(HttpMarshalling.toJson(AnswerInlineQuery(inlineQuery.id, results, 1)))
    request(AnswerInlineQuery(inlineQuery.id, results, 1))
  }
}
