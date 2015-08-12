package info.mukel.telegram.bots

import info.mukel.telegram.bots.api.{TelegramBotApi, Update}
import info.mukel.telegram.bots.http.ScalajHttpClient

/**
 * TelegramBot
 */
abstract class TelegramBot(val token: String) extends TelegramBotApi(token) with ScalajHttpClient with Runnable {
  lazy val botName: String = getMe.username.get // explode if username is not specified
  def handleUpdate(update: Update): Unit
}