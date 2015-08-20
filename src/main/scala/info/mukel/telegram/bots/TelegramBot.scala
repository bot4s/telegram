package info.mukel.telegram.bots

import info.mukel.telegram.bots.api.{TelegramBotApi, Update}
import info.mukel.telegram.bots.http.ScalajHttpClient

/**
 * TelegramBot
 */
abstract class TelegramBot(val token: String) extends TelegramBotApi(token) with ScalajHttpClient {

  lazy val botName: String = getMe.username.get

  /**
   * handleUpdate
   *
   * Process incoming updates (comming from polling, webhooks...)
   */  
  def handleUpdate(update: Update): Unit
}
