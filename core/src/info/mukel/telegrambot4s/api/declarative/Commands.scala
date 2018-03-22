package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.api.BotExecutionContext
import info.mukel.telegrambot4s.methods.GetMe
import info.mukel.telegrambot4s.models.{Message, User}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

case class Command(cmd: String, recipient: Option[String])

/**
  * Provides a declarative interface to define commands.
  */
trait Commands extends Messages with BotExecutionContext with CommandImplicits {

  private lazy val self: User =
    Await.result(request(GetMe), 10.seconds)

  /**
    * Receives /commands with the specified action.
    * Commands '/' prefix is optional. "cmd" == "/cmd" == 'cmd
    * Implicits are provided for "string" and 'symbol.
    * @example {{{
    *   onCommand("/command") { implicit msg => ... }
    *   onCommand("command") { implicit msg => ... }
    *   onCommand('echo) { implicit msg => ... }
    *   onCommand('hi | 'hello | 'hey) { implicit msg => ... }
    *   onCommand("/adieu" | "/bye") { implicit msg => ... }
    * }}}
    */
  def onCommand(filter: Filter[Command])(action: Action[Message]): Unit = {
    onMessage { implicit msg =>
      using(command) { cmd =>
        if (filter(cmd)) {
          action(msg)
        }
      }
    }
  }

  /**
    * Extract command arguments from the message's text; if present.
    * The first token, the /command, is dropped.
    *
    * @example {{{
    *   on('echo) { implicit msg =>
    *     withArgs { args =>
    *       reply(args.mkString(" "))
    *     }
    *   }
    * }}}
    */

  def withArgs(action: Action[Args])(implicit msg: Message): Unit = {
    using(commandArguments)(action)
  }

  /**
    * Extracts the leading /command.
    */
  def command(msg: Message): Option[Command] = {
    msg.text.flatMap { text =>
      val cmdRe = """^(?:\s*/)(\w+)(?:@(\w+))?""".r // /cmd@recipient
      cmdRe.findFirstIn(text) flatMap  {
        case cmdRe(cmd, recipient) => Some(Command(cmd, Option(recipient)))
        case _ => None
      }
    }
  }

  override def run(): Future[Unit] = {
    val eol = super.run()
    if (self == null) { // force lazy val
      throw new RuntimeException("Commands initialization failed: GetMe failed.")
    }
    assert(self.username.isDefined, "bot username is not defined")
    eol
  }

  /**
    * Tokenize message text; drops first token (/command).
    */
  def commandArguments(msg: Message): Option[Args] = textTokens(msg).map(_.tail)

  /**
    * Tokenize message text.
    */
  def textTokens(msg: Message): Option[Args] = msg.text.map(_.trim.split("\\s+"))

  val respectRecipient: CommandFilter = CommandFilter.ANY.to(self.username)
}

trait CommandFilter extends Filter[Command] {
  self =>
  def or(other: CommandFilter): CommandFilter = CommandFilter(t => self(t) || other(t))
  def and(other: CommandFilter): CommandFilter = CommandFilter(t => self(t) && other(t))
  def |(other: CommandFilter): CommandFilter = or(other)
  def &(other: CommandFilter): CommandFilter = and(other)
  def not(other: CommandFilter): CommandFilter = CommandFilter(v => !self(v))
  def unary_!(other: CommandFilter): CommandFilter = not(other)

  def to(r: => Option[String]): CommandFilter = {
    CommandFilter(_.recipient.forall(
        t => r.forall(t.equalsIgnoreCase)))
  }

  def @@(r: => Option[String]): CommandFilter = to(r)
}

trait CommandImplicits {
  implicit def stringToCommandFilter(s: String): CommandFilter = CommandFilter {
    val target = s.trim().stripPrefix("/")

    require(target.matches("""\w+"""))

    PartialFunction.cond(_) {
      case Command(cmd, _) if target.equalsIgnoreCase(cmd) => true
    }
  }

  implicit def symbolToCommandFilter(s: Symbol): CommandFilter = {
    stringToCommandFilter(s.name)
  }
}

object CommandFilter {
  def apply(f: Filter[Command]): CommandFilter = new CommandFilter {
    override def apply(c: Command): Boolean = f(c)
  }
  val ANY = CommandFilter(_ => true)
  val NONE = CommandFilter(_ => false)
}
