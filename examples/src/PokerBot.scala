import com.bot4s.telegram.api.Polling
import com.bot4s.telegram.api.declarative.{Callbacks, Commands}
import com.bot4s.telegram.methods.SendGame
import com.bot4s.telegram.models._

import scala.concurrent.Future

class PokerBot(token: String) extends ExampleBot(token) with Polling with Commands with Callbacks {

  onCommand("/start") { implicit msg =>
    // Note that the button doesn't contain the game_short_name.
    val playNowBtn = InlineKeyboardButton.callbackGame("🎮 Play now!")
    val pepeBtn = InlineKeyboardButton.callbackData("🎮 Play now!", "pepe")
    // or equivalent InlineKeyboardButton("Play Now!", callbackGame = Some(CallbackGame))

    val inlineBtns = InlineKeyboardMarkup(Seq(Seq(playNowBtn), Seq(pepeBtn)))

    request(
      SendGame(msg.source,
        gameShortName = "play_2048",
        replyMarkup = Some(inlineBtns)))
  }

  onCallbackQuery { implicit callbackQuery =>
    println(s"gameShortName: ${callbackQuery.gameShortName}")
    println(s"data: ${callbackQuery.data}")

    callbackQuery.data.foreach { data =>
      ackCallback(text = Some("Hello Pepe"), url = Some("https://t.me/MenialBot?start=1234"))
    }

    // You must acknowledge callback queries, even if there's no response.
    // e.g. just ackCallback()

    // To open game, you may need to pass extra (url-encoded) information to the game.
    //ackCallback(url = Some("https://my.awesome.game.com/awesome"))
  }

  def replyWithGame(gameShortName: String,
                    disableNotification: Option[Boolean] = None,
                    replyToMessageId: Option[Int] = None,
                    replyMarkup: Option[ReplyMarkup] = None)
                   (implicit msg: Message): Future[Message] = {
    request(
      SendGame(msg.source,
        gameShortName = gameShortName,
        replyMarkup = replyMarkup,
        disableNotification = disableNotification,
        replyToMessageId = replyToMessageId
      ))
  }


  onCommand('trivia) { implicit msg =>
    replyWithGame("trivia_game")
  }

  onCommand('tictactoe | 'xoxo) { implicit msg =>
    replyWithGame("tictactoe_game")
  }
}