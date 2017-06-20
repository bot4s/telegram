package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.models.Message

import scala.collection.mutable

/**
  * Makes a bot command-aware using a nice declarative interface.
  * Adds an auto-generated "/help" command to list all declared commands with the given description.
  * Commands without provided description will not be listed.
  */
@deprecated trait Commands extends VanillaCommands {
  on("/help", "list available commands") { implicit msg => _ =>
    val help = for ((cmd, desc) <- commands)
      yield
        s"$cmd - $desc"
    reply(help mkString "\n")
  }
}

/**
  * Makes a bot command-aware using a nice declarative interface.
  * Does NOT include the auto-generated "/help" command.
  */
@deprecated trait VanillaCommands extends Messages {

  private type Args = Seq[String]
  private type MessageActionWithArgs = Message => Args => Unit

  // command -> description
  private[api] val commands = mutable.Map[String, String]()

  private def commandFilter(command: String, parser: CommandParser): MessageFilter = {
    case parser(rawCommand, _)
      if parser.matchCommands(rawCommand, command) => true
    case _ => false
  }

  private def commandAction(actionWithArgs: MessageActionWithArgs, parser: CommandParser): MessageAction  = {
    case msg @ parser(_, args) => actionWithArgs(msg)(args)
    case _ =>
  }

  /**
    * Makes the bot able react to 'command' with the specified handler.
    *
    * @param command         Command, should include the "/" prefix, e.g. "/command"
    * @param description     Provides a brief insight about the command functioning.
    *                        Hidden (null) by default.
    * @param actionWithArgs  Handler, receives the incoming message and the command's arguments.
    */
  def on(command: String, description: String = null, parser: CommandParser = CommandParser.Default)
        (actionWithArgs: MessageActionWithArgs): Unit = {

    def noWhitespace(s: String): Boolean = "\\s".r.findFirstIn(s).isEmpty

    require(noWhitespace(command),
      "Commands must not contain whitespaces")

    whenMessage(commandFilter(command, parser))(commandAction(actionWithArgs, parser))

    if (description.ne(null))
      commands += (command -> description)
  }
}

/**
  * Bindings for custom command parsers.
  * Command parsers extract a command and arguments from a message.
  * This is intended for very specific use cases:
  *   e.g. Embedding arguments in commands /cmd-123 -> /cmd 123
  *
  * The 'default' command parser will satisfy most common use cases.
  */
trait CommandParser {
  type Args = Seq[String]
  type CommandWithArgs = (String, Args)

  /** Extractor to get commands and arguments from Messages.
    *
    * @param msg Message
    * @return command -> args pair extracted from the Message,
    *         or None if no command was found.
    */
  def unapply(msg: Message): Option[CommandWithArgs]

  /**
    * Commands can arrive with mixed casing, with a @sender suffix.
    * This methods is responsible for matching commands.
    * Commands are case insensitive.
    *
    * @param fromParser Raw command give from parser
    * @param target     Command to test
    * @return           true if the commands are the same, false otherwise.
    */
  def matchCommands(fromParser: String, target: String): Boolean =
    removeSender(fromParser).toLowerCase == target.toLowerCase

  private def removeSender(cmd: String): String =
    cmd.takeWhile(_ != '@')
}

object CommandParser {

  /**
    * Command is the first 'token'.
    * Remaining tokens (arguments) are separated by spaces.
    */
  object Default extends CommandParser {
    def unapply(msg: Message): Option[CommandWithArgs] = {
      for {
        text <- msg.text
        Array(cmd, args @ _*) = text.trim.split("\\s+").filter(_.nonEmpty)
      } yield
        (cmd -> args)
    }
  }

  /**
    * One argument per line.
    */
  object WholeLineArguments extends CommandParser {
    def unapply(msg: Message): Option[CommandWithArgs] = {
      for {
        msgText <- msg.text
        // Remove heading whitespace, including newlines
        text = msgText.dropWhile(_.isWhitespace)
        Array(line0, args @ _ *) = text.split("(?m)$")
        (cmd, arg0) = line0.span(!_.isWhitespace)
      } yield
        cmd -> (arg0 +: args)
    }
  }

  /**
    * First line is tokenized by spaces like Default.
    * Remaining lines are passed as arguments.
    * e.g.
    *   /command arg1 arg2
    *   this is arg3
    *   and then arg4
    */
  object Hybrid extends CommandParser {
    def unapply(msg: Message): Option[CommandWithArgs] = {
      for {
        msgText <- msg.text
        // Remove heading whitespace, including newlines
        text = msgText.dropWhile(_.isWhitespace)
        if text.nonEmpty
        // firstSegment is tokenized using the "default" method
        Array(firstSegment, wholeLineArgs @ _ *) = text.split("\\R")
        Array(cmd, args @ _*) = firstSegment.trim.split("\\s+").filter(_.nonEmpty)
      } yield
        cmd -> (args ++ wholeLineArgs)
    }
  }
}
