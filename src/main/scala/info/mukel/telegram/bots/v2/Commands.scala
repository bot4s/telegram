package info.mukel.telegram.bots.v2

import info.mukel.telegram.bots.v2.api.Implicits._
import info.mukel.telegram.bots.v2.methods.ParseMode.ParseMode
import info.mukel.telegram.bots.v2.methods.SendMessage
import info.mukel.telegram.bots.v2.model.Message

import scala.collection.mutable
import scala.concurrent.Future

/**
  * Commands
  *
  * Makes a bot command-aware using a nice declarative interface
  */
trait Commands extends TelegramBot {

  type Action = Message => (Seq[String] => Unit)

  // All commands must be preceeded by 'cmdPrefix' eg. /hello
  val cmdPrefix = "/"

  // Allows targeting specific bots eg. /hello@FlunkeyBot
  val cmdAt = "@"

  private val commands = mutable.HashMap[String, Action]()

  /**
    * handleMessage
    *
    * Parses messages and spawns bot commands accordingly, DOES NOT support targeting specific bots.
    * Commands are case INSENSITIVE, additional parameters are NOT.
    *
    * General syntax:
    * /command[@BotUsername]* args*
    *
    * Assuming cmdPrefix = '/' and cmdAt = '@' here are some usage examples:
    *
    * Example 'command':
    * /command arg0 arg1
    */
  override def handleMessage(message: Message): Unit = {
    message.text map { text =>
      // TODO: Allow parameters with spaces e.g. /echo "Hello World"
      val tokens = text.trim split " "
      tokens match {
        case Array(rawCmd, args@_*) if rawCmd startsWith cmdPrefix =>
          val cmd = rawCmd.stripPrefix(cmdPrefix).toLowerCase
          for (action <- commands.get(cmd))
            action(message)(args)

        // If the command is not found ignore the message

        case _ => // Avoid silent exception, fallback to previous handler
          super.handleMessage(message)
      }
    } getOrElse (super.handleMessage(message)) // No text, fallback to previous handler
  }

  /**
    * replyTo
    *
    * Handy wrapper to send text replies
    */
  def reply(text: String,
            parseMode: Option[ParseMode] = None,
            disableWebPagePreview: Option[Boolean] = None,
            disableNotification: Option[Boolean] = None,
            replyToMessageId: Option[Long] = None)
           (implicit message: Message): Future[Message] = {
    api.request(SendMessage(message.chat.id, text, parseMode, disableWebPagePreview, disableNotification, replyToMessageId))
  }

  /**
    * on
    *
    * Makes the bot able react to 'command' with the specified handler.
    * 'action' will receive the sender (chatId) and the arguments as parameters.
    */
  def on(command: String)(action: Action): Unit = {
    commands += (command -> action)
  }
}
