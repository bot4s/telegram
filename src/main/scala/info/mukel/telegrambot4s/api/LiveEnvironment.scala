package info.mukel.telegrambot4s.api

import com.typesafe.scalalogging.Logger

/**
  * 'Live' layer (network + logging) for bots.
  */
trait LiveEnvironment {
  _ : BotBase with AkkaDefaults =>

  override val logger = Logger[LiveEnvironment]
  override val request = new HttpClientQueued(token) //new TelegramApiAkka(token)
}
