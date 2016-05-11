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

  private val commands = mutable.HashMap[String, Action]()

  /**
    * handleMessage
    *
    * Parses messages and spawns bot commands accordingly.
    * Commands are case-iNsEnSiTiVe.
    *
    * Example 'command':
    * /command arg0 arg1
    */
  override def handleMessage(message: Message): Unit = {
    val accepted = for {
      text <- message.text
      Array(cmd, args @ _*) = text.trim.split(" ")
      action <- commands.get(cmd.toLowerCase)
    } yield
      action(message)(args)

    // Fallback to upper level to preserve trait stack-ability
    accepted.getOrElse(super.handleMessage(message))
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
    * 'action' receives a message and the arguments as parameters.
    */
  def on(command: String)(action: Action): Unit = {
    commands += (command -> action)
  }
}
