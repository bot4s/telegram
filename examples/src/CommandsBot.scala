import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.api.declarative.{ Commands, RegexCommands }
import com.bot4s.telegram.future.Polling

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.Try
import com.bot4s.telegram.methods.SetMyCommands
import com.bot4s.telegram.models.BotCommand

/**
 * Showcases different ways to declare commands (Commands + RegexCommands).
 *
 * Note that non-ASCII commands are not clickable.
 *
 * @param token Bot's token.
 */
class CommandsBot(token: String)
    extends ExampleBot(token)
    with Polling
    with Commands[Future]
    with RegexCommands[Future] {

  // Extractor
  object Int {
    def unapply(s: String): Option[Int] = Try(s.toInt).toOption
  }

  request(
    SetMyCommands(
      List(
        BotCommand("hello", "Welcome someone"),
        BotCommand("hola", "You guessed it"),
        BotCommand("metro", "I'm late to work. Give me the train's schedule"),
        BotCommand("Beer", "Let me see the menu")
      )
    )
  ).void

  // String commands.
  onCommand("/hello") { implicit msg =>
    reply("Hello America!").void
  }

  // '/' prefix is optional
  onCommand("hola") { implicit msg =>
    reply("Hola Mundo!").void
  }

  // Several commands can share the same handler.
  // Shows the 'using' extension to extract information from messages.
  onCommand("/hallo" | "/bonjour" | "/ciao" | "/hola") { implicit msg =>
    using(_.from) { // sender
      user =>
        reply(s"Hello ${user.firstName} from Europe?").void
    }
  }

  // Also using Symbols; the "/" prefix is added by default.
  onCommand("привет") { implicit msg =>
    reply("\uD83C\uDDF7\uD83C\uDDFA").void
  }

  // Note that non-ascii commands are not clickable.
  onCommand("こんにちは" | "你好" | "안녕하세요") { implicit msg =>
    reply("Hello from Asia?").void
  }

  // Different spellings + emoji commands.

  onCommand("/metro" | "/métro" | "/🚇") { implicit msg =>
    reply("Metro schedule bla bla...").void
  }

  onCommand("beer" | "beers" | "🍺" | "🍻") { implicit msg =>
    reply("Beer menu bla bla...").void
  }

  // withArgs extracts command arguments.
  onCommand("echo") { implicit msg =>
    withArgs { args =>
      reply(args.mkString(" ")).void
    }
  }

  // withArgs with pattern matching.
  onCommand("/inc") { implicit msg =>
    withArgs {
      case Seq(Int(i)) =>
        reply("" + (i + 1)).void

      // Conveniently avoid MatchError, providing hints on usage.
      case _ =>
        reply("Invalid argument. Usage: /inc 123").void
    }
  }

  // Regex commands also available.
  onRegex("""/timer\s+([0-5]?[0-9]):([0-5]?[0-9])""".r) { implicit msg =>
    { case Seq(Int(mm), Int(ss)) =>
      reply(s"Timer set: $mm minute(s) and $ss second(s)").void
      Utils.after(mm.minutes + ss.seconds) {
        reply("Time's up!!!")
      }
    }
  }
}
