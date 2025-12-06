import cats.Applicative
import cats.effect.{ Concurrent, ContextShift, Timer }
import cats.syntax.flatMap._
import cats.syntax.functor._
import com.bot4s.telegram.models.Message
import com.bot4s.telegram.api.declarative._
import com.bot4s.telegram.api.declarative.CommandFilterMagnet._
import com.bot4s.telegram.api.declarative.{ Commands, RegexCommands }
import com.bot4s.telegram.cats.Polling

import scala.concurrent.duration._
import scala.util.Try
import sttp.client4.Backend
import com.bot4s.telegram.cats.TelegramBot

/**
 * Showcases different ways to declare commands (Commands + RegexCommands).
 *
 * Note that non-ASCII commands are not clickable.
 *
 * @param token Bot's token.
 */
class CommandsBot[F[_]: Concurrent: Timer: ContextShift](token: String, backend: Backend[F])
    extends TelegramBot[F](token, backend)
    with Polling[F]
    with Commands[F]
    with RegexCommands[F] {

  // Extractor
  object Int {
    def unapply(s: String): Option[Int] = Try(s.toInt).toOption
  }

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

  def secretIsValid(msg: Message) =
    Applicative[F].pure(msg.text.fold(false)(_.split(" ").last == "password"))

  whenF(onCommand("secret"), secretIsValid) { implicit msg =>
    reply("42").void
  }

  onCommand("/metro") { implicit msg =>
    reply("Metro schedule bla bla...").void
  }

  onCommand("beer" | "beers") { implicit msg =>
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
      for {
        _ <- reply(s"Timer set: $mm minute(s) and $ss second(s)")
        _ <- implicitly[Timer[F]].sleep(mm.minutes + ss.seconds)
        _ <- reply("Time's up!")
      } yield ()
    }
  }

  // Handles only /respect2@recipient commands
  onCommand("respect" & respectRecipient(Some("recipient"))) { implicit msg =>
    reply("Respectful command").void
  }

  // Handles only /respect@<current-botname> commands
  onCommand("respect2" & RespectRecipient) { implicit msg =>
    reply("Respectful command #2").void
  }
}
