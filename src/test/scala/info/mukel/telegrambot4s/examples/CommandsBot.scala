package info.mukel.telegrambot4s.examples

import info.mukel.telegrambot4s.Implicits.{Extractor => $}
import info.mukel.telegrambot4s.api.{BetterCommands, Polling, RegexCommands}
import scala.concurrent.duration._

/**
  * Showcases different ways to declare commands (BetterCommands + RegexCommands).
  *
  * Note that non-ASCII commands are not clickable.
  *
  * @param token Bot's token.
  */
class CommandsBot(token: String) extends ExampleBot(token)
  with Polling with BetterCommands with RegexCommands {

  // String commands.
  on("/hello") { implicit msg =>
    reply("Hello America!")
  }

  // Several commands can share the same handler.
  // Shows the 'using' extension to extract information from messages.
  on("/hallo" :: "/bonjour" :: "/ciao" :: "/hola" :: Nil) {
    implicit msg =>
      using(_.from) { // sender
        user =>
          reply(s"Hello ${user.firstName} from Europe?")
      }
  }

  // Also using Symbols; the "/" prefix is added by default.
  // Note that non-ascii commands are not clickable.
  on('привет) { implicit msg =>
    reply("\uD83C\uDDF7\uD83C\uDDFA")
  }

  on('こんにちは :: '你好 :: '안녕하세요 :: Nil) { implicit msg =>
    reply("Hello from Asia?")
  }

  // Different spellings + emoji commands.

  on("/metro" :: "/métro" :: "/🚇" :: Nil) { implicit msg =>
      reply("Metro schedule bla bla...")
  }

  on("/beer" :: "/beers" :: "/🍺" :: "/🍻" :: Nil) { implicit msg =>
    reply("Beer menu bla bla...")
  }

  // withArgs extracts command arguments.
  on('echo) { implicit msg =>
    withArgs {
      args =>
        reply(args.mkString(" "))
    }
  }

  // withArgs with pattern matching.
  on("/inc") { implicit msg =>
    withArgs {
      case Seq($.Int(i)) =>
        reply("" + (i+1))

      // Conveniently avoid MatchError, providing hints on usage.
      case _ =>
        reply("Invalid argument. Usage: /inc 123")
    }
  }

  // Regex commands also available.
  onRegex("""/timer\s+([0-5]?[0-9]):([0-5]?[0-9])""".r) { implicit msg => {
    case Seq($.Int(mm), $.Int(ss)) =>
      reply(s"Timer set: $mm minute(s) and $ss second(s)")
      system.scheduler.scheduleOnce(mm.minutes + ss.seconds) {
        reply("Time's up!!!")
      }
  }}
}
