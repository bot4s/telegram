package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.models.Message

trait Commands extends Messages {

  import info.mukel.telegrambot4s.api.Extractors._

  /**
    * React to /commands with the specified action
    *
    * @example {{{
    *   on('echo) { implicit msg => ... }
    *   on('hello :: 'hi :: 'hey :: Nil) { ... }
    *   on(Seq("/beer", "/beers")) { ... }
    * }}}
    */
  def onCommand[T : ToCommand](c: T)(action: MessageAction): Unit = {
    val variants = implicitly[ToCommand[T]].apply(c)

    onMessage { implicit msg =>
      using(textTokens) { tokens =>
        val cmd = tokens.head
        val cleanCmd = cmd.trim.stripPrefix(ToCommand.CommandPrefix).takeWhile('@' != _).toLowerCase
        if (variants.exists(_.toLowerCase == cleanCmd))
          action(msg)
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
  def apply(t: T): Seq[String]
}

object ToCommand {
  val CommandPrefix = "/"

  implicit object stringToCommand extends ToCommand[String] {
    def apply(s: String): Seq[String] = stringsToCommand(Seq(s))
  }

  implicit object stringsToCommand extends ToCommand[Seq[String]] {
    def apply(s: Seq[String]): Seq[String] = s.map(_.stripPrefix(CommandPrefix))
  }

  implicit object symbolToCommand extends ToCommand[Symbol] {
    def apply(s: Symbol): Seq[String] = stringToCommand(s.name)
  }

  implicit object symbolsToCommand extends ToCommand[Seq[Symbol]] {
    def apply(s: Seq[Symbol]): Seq[String] = stringsToCommand(s.map(_.name))
  }
}