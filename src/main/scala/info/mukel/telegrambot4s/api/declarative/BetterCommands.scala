package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.models.Message

import scala.language.implicitConversions

trait BetterCommands extends Messages {
  /**
    * React to /commands with the specified action
    *
    * @example {{{
    *   on('echo) { msg => ... }
    *   on('hello :: 'hi :: 'hey :: Nil) { ... }
    *   on(Seq("/beer", "/beers")) { ... }
    * }}}
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
  def using[T](extractor: Message => Option[T])(actionT: Action[T])(implicit msg: Message): Unit = {
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

sealed trait CommandMagnet[Context] {
  type Result
  def apply(ctx: Context): Result
}

object CommandMagnet {

  implicit def fromStrings[Context <: Messages](commands: Seq[String]) =
    new CommandMagnet[Context] {
      type Result = Action[MessageAction]

      def apply(ctx: Context): Result = {
        action =>
          ctx.onMessage { msg =>
            for (text <- msg.text) {
              val Array(cmd, args @ _ *) = text.trim.split("\\s+")
              val cleanCmd = cmd.takeWhile('@' != _).toLowerCase
              if (commands.exists(_.toLowerCase == cleanCmd))
                action(msg)
            }
          }
      }
    }

  implicit def fromString[Context <: Messages](command: String) = fromStrings[Context](Seq(command))

  implicit def fromSymbol[Context <: Messages](sym: Symbol) = fromSymbols[Context](Seq(sym))

  implicit def fromSymbols[Context <: Messages](syms: Seq[Symbol]) = fromStrings[Context](syms.map("/" + _.name))
}