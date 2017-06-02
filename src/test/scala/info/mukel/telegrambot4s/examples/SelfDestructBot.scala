package info.mukel.telegrambot4s.examples

import java.time.Instant

import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.methods._
import info.mukel.telegrambot4s.models.UpdateType.Filters._
import info.mukel.telegrambot4s.models._

import scala.concurrent.duration._

/**
  * Send self-destructing messages.
  * Ported from: https://github.com/Pitasi/selfdestructbot
  */
class SelfDestructBot(token: String) extends ExampleBot(token) with Polling {

  override def allowedUpdates = InlineUpdates ++ CallbackUpdates

  val timeouts = Seq(3, 5, 10, 30)

  def now = Instant.now().getEpochSecond

  def button(t: Long) = InlineKeyboardButton.callbackData("⏳ left?", t+"")

  def buildResult(timeout: Int, msg: String): InlineQueryResult = {
    InlineQueryResultArticle(s"$timeout", s"$timeout seconds",
      inputMessageContent = InputTextMessageContent(msg),
      description = s"Message will be deleted in $timeout seconds",
      replyMarkup = InlineKeyboardMarkup(button(now)))
  }

  override def onCallbackQuery(cbq: CallbackQuery): Unit = {
    // super.onCallbackQuery(cbq)
    val left = cbq.data.map(_.toLong - now).getOrElse(-1L)
    request(AnswerCallbackQuery(cbq.id, s"$left seconds remaining.", cacheTime = 0))
  }

  override def onChosenInlineResult(result: ChosenInlineResult): Unit = {
    // super.onChosenInlineResult(result)

    val delay = result.resultId.toInt
    request(EditMessageReplyMarkup(
      inlineMessageId = result.inlineMessageId,
      replyMarkup = InlineKeyboardMarkup(button(now + delay))))

    system.scheduler.scheduleOnce(delay.seconds) {
      request(EditMessageText(
        text = "⌛ Expired",
        inlineMessageId = result.inlineMessageId))
    }
  }

  override def onInlineQuery(q: InlineQuery): Unit = {
    // super.onInlineQuery(q)

    val results = if (q.query.isEmpty)
      Seq.empty
    else
      timeouts.map(buildResult(_, q.query))

    request(AnswerInlineQuery(q.id, results, 5))
  }
}
