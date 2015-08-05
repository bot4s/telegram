import ChatAction.ChatAction

abstract class TelegramBot(token: String) extends TelegramBotAPI(token) with ScalajHttpClient with Polling with Runnable {

  import OptionPimps._

  def handleUpdate(update: Update): Unit

  val pollingCycle = 1000

  lazy val botName = {
    val user = getMe
    user.username.get
  }

  private var running = true

  override def run(): Unit = {
    println("Running: " + botName)

    var updatesOffset = 0

    while (running) {
      for (u <- getUpdates(offset = updatesOffset)) {
        handleUpdate(u)
        updatesOffset = updatesOffset max (u.update_id + 1)
      }

      Thread.sleep(pollingCycle)
    }
  }

  //def start(): Unit = (new Thread(this)).start()
  def stop(): Unit = (running = false)

  def setChatAction(chat_id: Int, chatAction: ChatAction): Unit = sendChatAction(chat_id, chatAction)
}