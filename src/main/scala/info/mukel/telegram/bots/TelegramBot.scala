package info.mukel.telegram.bots

import info.mukel.telegram.bots.api.{TelegramBotAPI, Update}
import info.mukel.telegram.bots.http.ScalajHttpClient

/**
 * TelegramBot
 */
abstract class TelegramBot(token: String) extends TelegramBotAPI(token) with ScalajHttpClient {
  def handleUpdate(update: Update): Unit
}