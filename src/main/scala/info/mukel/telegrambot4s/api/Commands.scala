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

  type Params = Seq[String]
  type Action = Message => (Params => Unit)

  private val commands = mutable.HashMap[String, Action]()

  /** Parses messages and runs bot commands accordingly.
    * Commands are case-iNsEnSiTiVe and the bot's name is stripped off.
    */
  override def onMessage(message: Message): Unit = {
    def cleanCmd(cmd: String): String = {
      val cmdEnd = if (cmd.contains('@')) cmd.indexOf('@') else cmd.length
      cmd.substring(0, cmdEnd).toLowerCase
    }

    val accepted = for {
      text <- message.text
      Array(cmd, args @ _*) = text.trim.split(" ")
      action <- commands.get(cleanCmd(cmd))
    } yield
      action(message)(args)

    // Fallback to upper level to preserve trait stack-ability
    accepted.getOrElse(super.onMessage(message))
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

  /** Makes the bot able react to 'command' with the specified handler.
    * 'action' receives a message and the arguments as parameters.
    */
  def on(command: String)(action: Action): Unit = {
    commands += (command -> action)
  }
}
