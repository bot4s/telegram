package info.mukel.telegrambot4s

import info.mukel.telegrambot4s.api.declarative.Commands
import info.mukel.telegrambot4s.api.{Polling, TelegramBot}

class DeleteMeBot extends TelegramBot with Polling with Commands {
  override def token: String = "211113992:AAFGyBTCb9D4MLLIYG8RKtTnQRkczVJ9t4I"

  var cnt = 0

  onCommand('hello) { implicit msg =>
    cnt += 1
    reply("Hello " + cnt)
  }

}
