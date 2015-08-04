import Status.Status

abstract class SimpleBot(token: String) extends TelegramBot(token) with Polling with Runnable {

  lazy val botName = {
    val user = getMe
    user.username.get
  }

  def handleUpdate(update: Update): Unit

  private var polling = true
  val pollingCycle = 5000

  override def run(): Unit = {

    println("Running: " + botName)

    var updatesOffset = 0

    while (polling) {
      for (u <- getUpdates(offset = Some(updatesOffset))) {
        handleUpdate(u)
        updatesOffset = updatesOffset max (u.update_id + 1)
      }

      Thread.sleep(pollingCycle)
    }
  }
  //def start(): Unit = (new Thread(this)).start()
  def stop(): Unit = (polling = false)

  def setStatus(chat_id: Int, status: Status): Unit = sendChatAction(chat_id, status)
}