package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.models.Message

import scala.language.implicitConversions

sealed trait CommandMagnet[Context] {
  type Result
  def apply(ctx: Context): Result
}

object CommandMagnet {

  implicit def fromStrings[Context <: Actions](commands: Seq[String]) =
    new CommandMagnet[Context] {
      type Result = Action[MessageAction]

      def apply(ctx: Context): Result = {
        action =>
          ctx.foreachMessage { msg =>
            for (text <- msg.text) {
              val Array(cmd, args @ _ *) = text.trim.split("\\s+")
              val cleanCmd = cmd.takeWhile('@' != _).toLowerCase
              if (commands.exists(_.toLowerCase == cleanCmd))
                action(msg)
            }
          }
      }
    }

  implicit def fromString[Context <: Actions](command: String) = fromStrings[Context](Seq(command))

  implicit def fromSymbol[Context <: Actions](sym: Symbol) = fromSymbols[Context](Seq(sym))

  implicit def fromSymbols[Context <: Actions](syms: Seq[Symbol]) = fromStrings[Context](syms.map("/" + _.name))
}

trait BetterCommands extends Actions {
  /**
    * Makes the bot able react to 'command' with the specified handler.
    */
  def on(magnet: CommandMagnet[this.type]): magnet.Result = magnet(this)

  /**
    * Tokenize message text.
    */
  def textTokens(msg: Message): Option[Args] = msg.text.map(_.trim.split("\\s+"))

  /**
    * Tokenize message text; drops first token (/command).
    */
  def commandArguments(msg: Message): Option[Args] = textTokens(msg).map(_.tail)

  def using[T](parser: Message => Option[T])(actionT: Action[T])(implicit msg: Message): Unit = {
    parser(msg).foreach(actionT)
  }

  /**
    * Extract command arguments.
    */
  def withArgs(action: Action[Args])(implicit msg: Message): Unit = {
    using(commandArguments)(action)
  }
}
