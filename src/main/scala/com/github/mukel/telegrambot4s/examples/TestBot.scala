package com.github.mukel.telegrambot4s.examples

import com.github.mukel.telegrambot4s.api.TelegramBot
import scala.io.Source

/**
  * Created by mukel on 5/9/16.
  */
trait TestBot extends TelegramBot {
  override def token: String =
    Source.fromFile("./tokens/menial_bot.token").getLines().next
}
