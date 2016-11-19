package info.mukel.telegrambot4s.examples

import com.typesafe.scalalogging.StrictLogging
import info.mukel.telegrambot4s.api.{AkkaDefaults, BotBase, Polling, TelegramBot}

import scala.io.Source

trait TestBot extends TelegramBot with App {
  lazy val menial_bot_token = Source.fromFile("menial_bot.token").getLines().next
  override def token: String = menial_bot_token
  run()
}
