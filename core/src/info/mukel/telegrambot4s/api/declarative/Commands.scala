package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.api.BotExecutionContext
import info.mukel.telegrambot4s.api.Extractors._
import info.mukel.telegrambot4s.methods.GetMe
import info.mukel.telegrambot4s.models.{Message, User}

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Provides a declarative interface to define commands.
  */
trait Commands extends Messages with BotExecutionContext with CommandFilters {

  lazy val self: User =
    Await.result(request(GetMe), 10.seconds)

  /**
    * React to /commands with the specified action.
    * Commands '/' prefix is optional. "cmd" == "/cmd" == 'cmd
    * Accepts a "string", a 'symbol, or a sequence of strings or symbols.
    *
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
    * Command extractor.
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

  /**
    * Tokenize message text; drops first token (/command).
    */
  def commandArguments(msg: Message): Option[Args] = textTokens(msg).map(_.tail)

  /**
    * Tokenize message text.
    */
  def textTokens(msg: Message): Option[Args] = msg.text.map(_.trim.split("\\s+"))
}

trait CommandFilter extends Filter[Command] {
  self =>
  def or(other: CommandFilter): CommandFilter = t => self(t) || other(t)
  def and(other: CommandFilter): CommandFilter = t => self(t) && other(t)
  def |(other: CommandFilter): CommandFilter = or(other)
  def &(other: CommandFilter): CommandFilter = and(other)
  def not(other: CommandFilter): CommandFilter = v => !self(v)
  def unary_!(other: CommandFilter): CommandFilter = not(other)
}

trait CommandFilters {
  val withoutRecipient: CommandFilter = cmd => cmd.recipient.isEmpty

  def withRecipient(recipient: => String): CommandFilter = {
    _.recipient.map(
      recipient.compareToIgnoreCase(_) == 0
    ).getOrElse(false)
  }

  implicit def stringToCommandFilter(s: String): CommandFilter = {
    val target = s.trim().stripPrefix("/")
    require(target.matches("""\w+"""))
    c => target.compareToIgnoreCase(c.cmd) == 0
  }

  implicit def symbolToCommandFilter(s: Symbol): CommandFilter = {
    stringToCommandFilter(s.name)
  }
}
