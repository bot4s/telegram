import zio._
import zio.interop.catz._
import com.bot4s.telegram.models.Message
import com.bot4s.telegram.api.declarative._
import com.bot4s.telegram.api.declarative.CommandFilterMagnet._
import com.bot4s.telegram.api.declarative.{ Commands, RegexCommands }
import com.bot4s.telegram.cats.Polling

import scala.util.Try

/**
 * Showcases different ways to declare commands (Commands + RegexCommands).
 *
 * Note that non-ASCII commands are not clickable.
 *
 * @param token Bot's token.
 */
class CommandsBot(token: String)
    extends ExampleBot(token)
    with Polling[Task]
    with Commands[Task]
    with RegexCommands[Task] {

  // Extractor
  object Int {
    def unapply(s: String): Option[Int] = Try(s.toInt).toOption
  }

  // String commands.
  onCommand("/hello") { implicit msg =>
    reply("Hello America!").ignore
  }

  // '/' prefix is optional
  onCommand("hola") { implicit msg =>
    reply("Hola Mundo!").ignore
  }

  // Several commands can share the same handler.
  // Shows the 'using' extension to extract information from messages.
  onCommand("/hallo" | "/bonjour" | "/ciao" | "/hola") { implicit msg =>
    using(_.from) { // sender
      user =>
        reply(s"Hello ${user.firstName} from Europe?").ignore
    }
  }

  def secretIsValid(msg: Message) =
    ZIO.succeed(msg.text.fold(false)(_.split(" ").last == "password"))

  whenF[Task, Message](onCommand("secret"), secretIsValid) { implicit msg =>
    reply("42").ignore
  }

  onCommand("/metro") { implicit msg =>
    reply("Metro schedule bla bla...").ignore
  }

  onCommand("beer" | "beers") { implicit msg =>
    reply("Beer menu bla bla...").ignore
  }

  // withArgs extracts command arguments.
  onCommand("echo") { implicit msg =>
    withArgs { args =>
      reply(args.mkString(" ")).ignore
    }
  }

  // withArgs with pattern matching.
  onCommand("/inc") { implicit msg =>
    withArgs {
      case Seq(Int(i)) =>
        reply("" + (i + 1)).ignore

      // Conveniently avoid MatchError, providing hints on usage.
      case _ =>
        reply("Invalid argument. Usage: /inc 123").ignore
    }
  }

  // Regex commands also available.
  onRegex("""/timer\s+([0-5]?[0-9]):([0-5]?[0-9])""".r) { implicit msg =>
    { case Seq(Int(mm), Int(ss)) =>
      for {
        _ <- reply(s"Timer set: $mm minute(s) and $ss second(s)")
        _ <- ZIO.sleep(mm.minutes + ss.seconds)
        _ <- reply("Time's up!")
      } yield ()
    }
  }

  // Handles only /respect2@recipient commands
  onCommand("respect" & respectRecipient(Some("recipient"))) { implicit msg =>
    reply("Respectful command").ignore
  }

  // Handles only /respect@<current-botname> commands
  onCommand("respect2" & RespectRecipient) { implicit msg =>
    reply("Respectful command #2").ignore
  }
}
