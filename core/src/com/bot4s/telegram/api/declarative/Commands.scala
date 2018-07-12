package com.bot4s.telegram.api.declarative

import com.bot4s.telegram.api.{BotBase, BotExecutionContext}
import com.bot4s.telegram.models.Message

import scala.concurrent.Future

case class Command(cmd: String, recipient: Option[String])

/**
  * Provides a declarative interface to define commands.
  */
trait Commands extends Messages with CommandImplicits {
  _: BotBase with BotExecutionContext =>

  /**
    * Receives /commands with the specified action.
    * Commands '/' prefix is optional. "cmd" == "/cmd" == 'cmd
    * Purely syntax honey for command filters.
    *
    * @example {{{
    *   onCommand("/command") { implicit msg => ... }
    *   onCommand("command") { implicit msg => ... }
    *   onCommand('echo) { implicit msg => ... }
    *   onCommand('hi | 'hello | 'hey) { implicit msg => ... }
    *   onCommand("/adieu" | "/bye") { implicit msg => ... }
    * }}}
    */
  def onCommand(filter: CommandFilterMagnet)(action: Action[Message]): Unit = {
    onMessage { implicit msg =>
      using(command) { cmd =>
        if (filter.accept(cmd)) {
          action(msg)
        }
      }
    }
  }

  /**
    * Receives /commands with the specified action.
    * Commands '/' prefix is optional. "cmd" == "/cmd" == 'cmd
    * Implicits are provided for "string" and 'symbol.
    * @example {{{
    *   onCommand(_.cmd.equalsIgnoreCase("hello")) { implicit msg => ... }
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

  /**
    * Tokenize message text; drops first token (/command).
    */
  def commandArguments(msg: Message): Option[Args] = textTokens(msg).map(_.tail)

  /**
    * Tokenize message text.
    */
  def textTokens(msg: Message): Option[Args] = msg.text.map(_.trim.split("\\s+"))

  abstract override def run(): Future[Unit] = {
    for {
      _ <- super.run()
    } yield {
      if (getMe == null) {
        throw new RuntimeException("Bot.self must be initialized")
      }
    }
  }

  val respectRecipient: CommandFilterMagnet = CommandFilterMagnet.ANY.to(getMe.username)
}

trait CommandFilterMagnet {
  self =>

  def accept(command: Command): Boolean

  def or(other: CommandFilterMagnet): CommandFilterMagnet = CommandFilterMagnet(t => self.accept(t) || other.accept(t))
  def and(other: CommandFilterMagnet): CommandFilterMagnet = CommandFilterMagnet(t => self.accept(t) && other.accept(t))
  def |(other: CommandFilterMagnet): CommandFilterMagnet = or(other)
  def &(other: CommandFilterMagnet): CommandFilterMagnet = and(other)
  def not(other: CommandFilterMagnet): CommandFilterMagnet = CommandFilterMagnet(v => !self.accept(v))
  def unary_!(other: CommandFilterMagnet): CommandFilterMagnet = not(other)

  def to(r: => Option[String]): CommandFilterMagnet = {
    CommandFilterMagnet(_.recipient.forall(
        t => r.forall(t.equalsIgnoreCase)))
  }

  def @@(r: => Option[String]): CommandFilterMagnet = to(r)
}

trait CommandImplicits {
  implicit def stringToCommandFilter(s: String): CommandFilterMagnet = CommandFilterMagnet {
    val target = s.trim().stripPrefix("/")

    require(target.matches("""\w+"""))

    PartialFunction.cond(_) {
      case Command(cmd, _) if target.equalsIgnoreCase(cmd) => true
    }
  }

  implicit def symbolToCommandFilter(s: Symbol): CommandFilterMagnet = {
    stringToCommandFilter(s.name)
  }
}

object CommandFilterMagnet {
  def apply(f: Filter[Command]): CommandFilterMagnet = new CommandFilterMagnet {
    override def accept(c: Command): Boolean = f(c)
  }
  val ANY = CommandFilterMagnet(_ => true)
}
