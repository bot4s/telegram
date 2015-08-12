package info.mukel.telegram.bots

import info.mukel.telegram.bots.api.ChatAction._
import info.mukel.telegram.bots.api.{TelegramBotApi, Update}

trait Polling {
  this: TelegramBot =>
  import OptionPimps._

  val pollingCycle = 1000
  private var running = true

  override def run(): Unit = {
    setWebhook(None)
    var updatesOffset = 0
    while (running) {
      for (u <- getUpdates(offset = updatesOffset)) {
        handleUpdate(u)
        updatesOffset = updatesOffset max (u.updateId + 1)
      }
      Thread.sleep(pollingCycle)
    }
  }

  //def start(): Unit = (new Thread(this)).start()
  def stop(): Unit = (running = false)
  def setChatAction(chat_id: Int, chatAction: ChatAction): Unit = sendChatAction(chat_id, chatAction)

}