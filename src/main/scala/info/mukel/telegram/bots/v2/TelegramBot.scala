package info.mukel.telegram.bots.v2

import info.mukel.telegram.bots.v2.api.TelegramApi

/**
  * Telegram Bot base
  */
trait TelegramBot {
  val token: String
  val api = new TelegramApi(token)
}
