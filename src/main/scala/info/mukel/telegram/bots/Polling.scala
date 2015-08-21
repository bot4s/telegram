package info.mukel.telegram.bots

import info.mukel.telegram.bots.api.ChatAction._
import info.mukel.telegram.bots.api.{TelegramBotApi, Update}

/**
 * Polling
 *
 * Provides updates by using polling (getUpdates) with a default cycle of 1s. 
 */
trait Polling extends Runnable {
  this : TelegramBot =>

  import OptionPimps._

  val pollingCycle = 1000
  private var running = true

  override def run(): Unit = {
    // setWebhook(None)
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
}