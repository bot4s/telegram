package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.models.Message
import scala.collection.mutable

/**
  * Makes a bot command-aware using a nice declarative interface.
  * Adds an auto-generated "/help" command to list all declared commands with the given description.
  * Commands without provide description will not be listed.
  */
trait Commands extends VanillaCommands {
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
trait VanillaCommands extends Actions {

  private type Args = Seq[String]
  private type ActionWithArgs = Message => Args => Unit

  // command -> description
  private[api] val commands = mutable.Map[String, String]()

  private object CommandMessage {
    def unapply(msg: Message): Option[(String, Args)] = {
      for {
        text <- msg.text
        Array(cmd, args @ _*) = text.trim.split(" ")
      } yield
        (cmd -> args)
    }
  }

  private def commandFilter(command: String): MessageFilter = {
    case CommandMessage(cmd, _) if cleanCmd(cmd) == command => true
    case _ => false
  }

  private def commandAction(actionWithArgs: ActionWithArgs): Action  = {
    case msg @ CommandMessage(_, args) => actionWithArgs(msg)(args)
    case _ =>
  }

  private def cleanCmd(cmd: String): String = cmd.takeWhile(_ != '@').toLowerCase

  /**
    * Makes the bot able react to 'command' with the specified handler.
    *
    * @param command         Command, should include the "/" prefix, e.g. "/command"
    * @param description     Provides a brief insight about the command functioning.
    * @param actionWithArgs  Handler, receives the incoming message and the command's arguments.
    */
  def on(command: String, description: String)(actionWithArgs: ActionWithArgs): Unit = {
    on(command)(actionWithArgs)
    commands += (command -> description)
  }

  /**
    * Makes the bot able react to 'command' with the specified handler.
    * A command without description will not appear in auto-generated /help command.
    *
    * @param command         Command, should include the "/" prefix, e.g. "/command"
    * @param actionWithArgs  Handler, receives the incoming message and the command's arguments.
    */
  def on(command: String)(actionWithArgs: ActionWithArgs): Unit =
    when(commandFilter(command))(commandAction(actionWithArgs))
}
