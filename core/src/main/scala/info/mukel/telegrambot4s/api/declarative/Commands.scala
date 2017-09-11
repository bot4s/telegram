package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.api.BotExecutionContext
import info.mukel.telegrambot4s.api.Extractors._
import info.mukel.telegrambot4s.methods.GetMe
import info.mukel.telegrambot4s.models.Message

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Provides a declarative interface to define commands.
  */
trait Commands extends Messages with BotExecutionContext {

  private lazy val botName: String =
    Await.result(request(GetMe).map(_.firstName), 10.seconds)

  /**
    * By default the @receiver suffix in commands is respected.
    * The bot won't process commands with a different recipient.
    * Set/override to true to receive all commands regardless of the @receiver.
    */
  val ignoreCommandReceiver = false

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
  def onCommand[T : ToCommand](commands: T*)(action: Action[Message]): Unit = {
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
          val optReceiver = ToCommand.getReceiver(cmd)
          val matchReceiver = ignoreCommandReceiver || 
            optReceiver.forall(_.equalsIgnoreCase(botName))

          if (matchReceiver && variants.contains(target))
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

  private[telegrambot4s] def cleanCommand(cmd: String): String = {
    cmd.trim.stripPrefix(CommandPrefix).toLowerCase.takeWhile('@' != _)
  }

  private[telegrambot4s] def getReceiver(cmd: String): Option[String] = {
    val parts = cmd.trim.stripPrefix(CommandPrefix).split("@")
    if (parts.size >= 2)
      Some(parts(1))
    else
      None
  }

  implicit object stringToCommand extends ToCommand[String] {
    def apply(s: String): String = cleanCommand(s)
  }

  implicit object symbolToCommand extends ToCommand[Symbol] {
    def apply(s: Symbol): String = stringToCommand(s.name)
  }
}