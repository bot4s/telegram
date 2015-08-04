import scala.collection.mutable

trait Commands {
  this : SimpleBot =>

  private val commands = mutable.HashMap[String, (Int, Seq[String]) => Unit]()
  val cmdPrefix = "/"

  override def handleUpdate(update: Update): Unit = {
    for {
      msg <- update.message
      text <- msg.text
    } /* do */ {

      println("Message: " + text)

      // TODO: Allow parameters with spaces e.g. /echo "Hello World"
      val tokens = text split " "
      tokens match {
        case Array(rawCmd, args @ _*) if rawCmd startsWith cmdPrefix =>
          val cmd = (rawCmd stripPrefix cmdPrefix).toLowerCase
          if (commands contains cmd)
            commands(cmd)(msg.chat.id, args)

        case _ => /* Ignore */

      }
    }
  }

  def replyTo(chat_id: Int, disable_web_page_preview : Option[Boolean] = None, reply_to_message_id: Option[Int] = None)(text: String): Option[Message] = {
    sendMessage(chat_id, text, disable_web_page_preview, reply_to_message_id)
  }

  def on(command: String)(action: (Int, Seq[String]) => Unit): Unit = {
    commands += (command -> action)
  }
}