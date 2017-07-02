package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.models.Message
import info.mukel.telegrambot4s.api.Extractors._

/**
  * Provides a declarative interface to define commands.
  */
trait Commands extends Messages {

  /**
    * React to /commands with the specified action.
    * Commands '/' prefix is optional. "cmd" == "/cmd" == 'cmd
    * Accepts a "string", a 'symbol, or a sequence of strings or symbols.
    *
    * @example {{{
    *   onCommand("/command") { implicit msg => ... }
    *   onCommand("command") { implicit msg => ... }
    *   onCommand('echo) { implicit msg => ... }
    *   onCommand('hi, 'hello, 'hey) { implicit msg => ... }
    *   onCommand("/adieu", "/bye") { implicit msg => ... }
    * }}}
    */
  def onCommand[T : ToCommand](commands: T*)(action: MessageAction): Unit = {
    require(commands.nonEmpty, "At least one command required")
    val toCommandImpl = implicitly[ToCommand[T]]
    val variants = commands.map(toCommandImpl.apply)

    require(variants.forall(_.forall(c => !c.isWhitespace)),
      "Commands cannot contain whitespace")

    onMessage { implicit msg =>
      using(textTokens) { tokens =>
        val cmd = tokens.head
        // Filter only commands
        if (cmd.startsWith(ToCommand.CommandPrefix)) {
          val target = ToCommand.cleanCommand(cmd)
          if (variants.contains(target))
            action(msg)
        }
      }
    }
  }

  /**
    * Generic extractor for messages.
    *
    * @example {{{
    *   on('hello) { implicit msg =>
    *     using(_.from) {
    *       user =>
    *         reply(s"Hello ${user.firstName}!")
    *     }
    *   }
    * }}}
    */
  def using[T](extractor: Extractor[Message, T])(actionT: Action[T])(implicit msg: Message): Unit = {
    extractor(msg).foreach(actionT)
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
}

trait ToCommand[-T] {
  def apply(t: T): String
}

object ToCommand {
  val CommandPrefix = "/"

  def cleanCommand(cmd: String): String =
    cmd.trim.stripPrefix(CommandPrefix).takeWhile('@' != _).toLowerCase

  implicit object stringToCommand extends ToCommand[String] {
    def apply(s: String): String = cleanCommand(s)
  }

  implicit object symbolToCommand extends ToCommand[Symbol] {
    def apply(s: Symbol): String = stringToCommand(s.name)
  }
}