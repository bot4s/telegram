package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.methods.ParseMode.ParseMode
import info.mukel.telegrambot4s.methods.SendMessage
import info.mukel.telegrambot4s.models.{Message, ReplyMarkup}

import scala.collection.mutable
import scala.concurrent.Future

/** Makes a bot command-aware using a nice declarative interface
  */
trait Commands extends BotBase {

  private type Args = Seq[String]
  private type Action = Message => Unit
  private type ActionWithArgs = Message => Args => Unit
  private type Handler = Either[Action, ActionWithArgs]
  private type Description = String
  private type Command = (Description, Handler)
  private type Filter = Message => Boolean

  val NoHelpAvailable = "no description"

  private val commands = mutable.Map[Filter, Command]()

  private object Command {
    def unapply(text: String): Option[(String, Args)] = {
      val Array(cmd, args @ _*) = text.trim.split(" ")
      Some((cmd, args))
    }
  }

  /** Parses messages and runs bot commands accordingly.
    * Commands are case-iNsEnSiTiVe and the bot's name is stripped off.
    */
  abstract override def onMessage(message: Message): Unit = {

    for {
      (filter, (_, action)) <- commands
      if filter(message)
    } /* do */ {
      action match {
        case Left(a) => // Action
          a(message)

        case Right(a) => // ActionWithArgs
          for {
            Command(_, args) <- message.text
          } /* do */ {
            a(message)(args)
          }

        case _ => logger.error("Unknown command handler type")
      }
    }

    // Fallback to upper level to preserve trait stack-ability
    super.onMessage(message)
  }

  /**
    * Handy wrapper to send text replies
    */
  def reply(text                  : String,
            parseMode             : Option[ParseMode] = None,
            disableWebPagePreview : Option[Boolean] = None,
            disableNotification   : Option[Boolean] = None,
            replyToMessageId      : Option[Long] = None,
            replyMarkup           : Option[ReplyMarkup] = None)
           (implicit message: Message): Future[Message] = {

    request(
      SendMessage(
        message.sender,
        text,
        parseMode,
        disableWebPagePreview,
        disableNotification,
        replyToMessageId,
        replyMarkup
      )
    )
  }

  private def cleanCmd(cmd: String): String = {
    val cmdEnd = if (cmd.contains('@')) cmd.indexOf('@') else cmd.length
    cmd.substring(0, cmdEnd).toLowerCase
  }

  /** Makes the bot able react to 'command' with the specified handler.
    * 'action' receives a message and the arguments as parameters.
    *
    * @param description Provides insights about the command functioning.
    */
  def on(command: String, description: String)(actionWithArgs: ActionWithArgs): Unit = {

    val f: Filter = { msg: Message =>
      val matches = for {
        Command(cmd, _) <- msg.text
      } yield
        cleanCmd(cmd) == command

      matches.getOrElse(false)
    }

    commands += ((f, (s"$command - $description", actionWithArgs)))
  }

  def on(command: String)(actionWithArgs: ActionWithArgs): Unit =
    on(command, NoHelpAvailable)(actionWithArgs)

  def on(filter: Filter, description: String)(action: Action): Unit = {
    commands += ((filter, (description, action)))
  }

  def on(filter: Filter)(action: Action): Unit = on(filter, NoHelpAvailable)(action)

  /** Simple auto-generated help command.
    */
  on("/help", "description of available commands") { implicit msg => _ =>
    val help =
      for {
        (_, (description, _)) <- commands
        if description.nonEmpty
      } yield
        description

    reply(help mkString "\n")
  }
}
