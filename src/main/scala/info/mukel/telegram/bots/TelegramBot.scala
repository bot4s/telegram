package info.mukel.telegram.bots

import info.mukel.telegram.bots.api.{TelegramBotApi, Update}
import info.mukel.telegram.bots.http.ScalajHttpClient

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

/**
 * TelegramBot
 *
 * Base for Telegram Bots
 */
abstract class TelegramBot(val token: String) extends TelegramBotApi(token) with ScalajHttpClient {

  lazy val botName: String = Await.result(getMe.map(_.username.get), 5.seconds)

  /**
   * handleUpdate
   *
   * Process incoming updates (comming from polling, webhooks...)
   */  
  def handleUpdate(update: Update): Unit
}
