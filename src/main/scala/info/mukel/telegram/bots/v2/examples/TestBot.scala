package info.mukel.telegram.bots.v2.examples

import info.mukel.telegram.bots.v2.TelegramBot

import scala.io.Source

/**
  * Created by mukel on 5/9/16.
  */
trait TestBot extends TelegramBot {
  override def token: String =
    Source.fromFile("./tokens/flunkey_bot.token").getLines().next
}
