import com.bot4s.telegram.api.declarative.{Commands, RegexCommands}
import com.bot4s.telegram.api.Polling

import scala.concurrent.duration._
import scala.util.Try

/**
  * Showcases different ways to declare commands (Commands + RegexCommands).
  *
  * Note that non-ASCII commands are not clickable.
  *
  * @param token Bot's token.
  */
class CommandsBot(token: String) extends ExampleBot(token)
  with Polling
  with Commands
  with RegexCommands {

  // Extractor
  object Int {
    def unapply(s: String): Option[Int] = Try(s.toInt).toOption
  }

  // String commands.
  onCommand("/hello") { implicit msg =>
    reply("Hello America!")
  }

  // '/' prefix is optional
  onCommand("hola") { implicit msg =>
    reply("Hola Mundo!")
  }

  // Several commands can share the same handler.
  // Shows the 'using' extension to extract information from messages.
  onCommand("/hallo" | "/bonjour" | "/ciao" | "/hola") {
    implicit msg =>
      using(_.from) { // sender
        user =>
          reply(s"Hello ${user.firstName} from Europe?")
      }
  }

  // Also using Symbols; the "/" prefix is added by default.
  onCommand('привет) { implicit msg =>
    reply("\uD83C\uDDF7\uD83C\uDDFA")
  }

  // Note that non-ascii commands are not clickable.
  onCommand('こんにちは | '你好 | '안녕하세요) { implicit msg =>
    reply("Hello from Asia?")
  }

  // Different spellings + emoji commands.

  onCommand("/metro" | "/métro" | "/🚇") { implicit msg =>
    reply("Metro schedule bla bla...")
  }

  onCommand("beer" | "beers" | "🍺" | "🍻") { implicit msg =>
    reply("Beer menu bla bla...")
  }

  // withArgs extracts command arguments.
  onCommand('echo) { implicit msg =>
    withArgs {
      args =>
        reply(args.mkString(" "))
    }
  }

  // withArgs with pattern matching.
  onCommand("/inc") { implicit msg =>
    withArgs {
      case Seq(Int(i)) =>
        reply("" + (i + 1))

      // Conveniently avoid MatchError, providing hints on usage.
      case _ =>
        reply("Invalid argument. Usage: /inc 123")
    }
  }

  // Regex commands also available.
  onRegex("""/timer\s+([0-5]?[0-9]):([0-5]?[0-9])""".r) { implicit msg => {
    case Seq(Int(mm), Int(ss)) =>
      reply(s"Timer set: $mm minute(s) and $ss second(s)")
      Utils.after(mm.minutes + ss.seconds) {
        reply("Time's up!!!")
      }
  }
  }
}
