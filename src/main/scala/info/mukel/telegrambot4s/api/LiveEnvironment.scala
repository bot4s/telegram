package info.mukel.telegrambot4s.api

import com.typesafe.scalalogging.Logger

/**
  * Created by mukel on 19.11.16.
  */
trait LiveEnvironment {
  _ : BotBase with AkkaDefaults =>

  override val logger = Logger[LiveEnvironment]
  override val api = new TelegramApiAkka(token)
}
