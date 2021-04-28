import java.time.Instant

import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.Implicits._
import com.bot4s.telegram.api.declarative.{ Callbacks, InlineQueries }
import com.bot4s.telegram.future.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models.UpdateType.Filters._
import com.bot4s.telegram.models._

import scala.concurrent.Future
import scala.concurrent.duration._

/**
 * Send self-destructing messages.
 * Ported from: https://github.com/Pitasi/selfdestructbot
 */
class SelfDestructBot(token: String)
    extends ExampleBot(token)
    with Polling
    with InlineQueries[Future]
    with Callbacks[Future] {

  val timeouts = Seq(3, 5, 10, 30)

  override def allowedUpdates = InlineUpdates ++ CallbackUpdates

  def buildResult(timeout: Int, msg: String): InlineQueryResult =
    InlineQueryResultArticle(
      s"$timeout",
      s"$timeout seconds",
      inputMessageContent = InputTextMessageContent(msg),
      description = s"Message will be deleted in $timeout seconds",
      replyMarkup = InlineKeyboardMarkup.singleButton(button(now))
    )

  def now = Instant.now().getEpochSecond

  def button(timeLeft: Long) = InlineKeyboardButton.callbackData("⏳ left?", "" + timeLeft)

  onCallbackQuery { implicit cbq =>
    val left = cbq.data.map(_.toLong - now).getOrElse(-1L)
    ackCallback(s"$left seconds remaining.", cacheTime = 0).void
  }

  onChosenInlineResult { implicit result =>
    val delay = result.resultId.toInt
    Utils.after(delay.seconds) {
      request(EditMessageText(text = "⌛ Expired", inlineMessageId = result.inlineMessageId))
    }

    request(
      EditMessageReplyMarkup(
        inlineMessageId = result.inlineMessageId,
        replyMarkup = InlineKeyboardMarkup.singleButton(button(now + delay))
      )
    ).void
  }

  onInlineQuery { implicit q =>
    val results =
      if (q.query.isEmpty)
        Seq.empty
      else
        timeouts.map(buildResult(_, q.query))
    answerInlineQuery(results, 5).void
  }
}
