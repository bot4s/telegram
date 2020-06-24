package com.bot4s.telegram.api.declarative

import com.bot4s.telegram.api.BotBase
import com.bot4s.telegram.models.Message

case class Command(cmd: String, recipient: Option[String])

/**
  * Provides a declarative interface to define commands.
  */
trait Commands[F[_]] extends Messages[F] {
  _: BotBase[F] =>

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
  def onCommand(filter: CommandFilterMagnet)(action: Action[F, Message]): Unit =
    onExtMessage { case (message, botUser) =>
      using(command) { cmd =>
        val appliedFilter = filter.to(botUser.flatMap(_.username))
        if (appliedFilter.accept(cmd)) {
          action(message)
        } else {
          unit
        }
      } (message)
    }

  /**
    * Receives /commands with the specified action.
    * Commands '/' prefix is optional. "cmd" == "/cmd" == 'cmd
    * Implicits are provided for "string" and 'symbol.
    * @example {{{
    *   onCommand(_.cmd.equalsIgnoreCase("hello")) { implicit msg => ... }
    * }}}
    */
  def onCommand(filter: Filter[Command])(action: Action[F, Message]): Unit = {
    onMessage { implicit msg =>
      using(command) { cmd =>
        if (filter(cmd)) {
          action(msg)
        } else {
          unit
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

  def withArgs(action: Action[F, Args])(implicit msg: Message): F[Unit] = {
    using(commandArguments)(action)
  }

  /**
    * Extracts the leading /command.
    */
  def command(msg: Message): Option[Command] = {
    msg.text.flatMap { text =>
      val cmdRe = """^(?:\s*/)(\w+)(?:@(\w+))?""".r // /cmd@recipient
      cmdRe.findFirstIn(text) flatMap {
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

trait CommandFilterMagnet {
  self =>

  def accept(command: Command): Boolean

  def or(other: CommandFilterMagnet): CommandFilterMagnet = new CommandFilterMagnet {
    override def accept(command: Command) = self.accept(command) || other.accept(command)
    override def to(r: Option[String]) = self.to(r).or(other.to(r))
  }
  def and(other: CommandFilterMagnet): CommandFilterMagnet = new CommandFilterMagnet {
    override def accept(command: Command) = self.accept(command) && other.accept(command)
    override def to(r: Option[String]) = self.to(r).and(other.to(r))
  }
  def |(other: CommandFilterMagnet) = or(other)
  def &(other: CommandFilterMagnet) = and(other)
  def not: CommandFilterMagnet = new CommandFilterMagnet {
    override def accept(command: Command) = !self.accept(command)
    override def to(r: Option[String]): CommandFilterMagnet = self.to(r).not
  }
  def unary_! = self.not

  def to(r: Option[String]): CommandFilterMagnet = this
  def @@(r: Option[String]) = to(r)
}

object CommandImplicits {
  implicit def stringToCommandFilter(s: String) = CommandFilterMagnet {
    val target = s.trim().stripPrefix("/")

    require(target.matches("""\w+"""))

    PartialFunction.cond(_) {
      case Command(cmd, _) if target.equalsIgnoreCase(cmd) => true
    }
  }

  implicit def symbolToCommandFilter(s: Symbol) = {
    stringToCommandFilter(s.name)
  }
}

object CommandFilterMagnet {
  def apply(f: Filter[Command]): CommandFilterMagnet = new CommandFilterMagnet {
    override def accept(c: Command): Boolean = f(c)
  }
  val ANY = CommandFilterMagnet(_ => true)

  def respectRecipient(recipient: Option[String]) = new CommandFilterMagnet {
    override def accept(command: Command) =
      command.recipient.forall(t => recipient.forall(t.equalsIgnoreCase))
  }

  val RespectRecipient = new CommandFilterMagnet {
    override def accept(command: Command) = true
    override def to(recipient: Option[String]) = respectRecipient(recipient)
  }
}
