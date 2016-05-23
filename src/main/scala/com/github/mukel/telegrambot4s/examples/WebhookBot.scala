package com.github.mukel.telegrambot4s.examples

import info.mukel.telegram.bots.v2.methods.SendMessage
import info.mukel.telegram.bots.v2.model.Message
import info.mukel.telegram.bots.v2.{Commands, TelegramBot, Webhook}
import info.mukel.telegram.bots.v2.api.Implicits._

/**
  * Created by mukel on 5/10/16.
  */
object WebhookBot extends TestBot with Webhook {

  def port = 8443
  def webhookUrl = "https://ed88ff73.ngrok.io"

  def toL337(s: String) =
    s.map("aegiost".zip("4361057").toMap.withDefault(identity))

  override def handleMessage(message: Message): Unit = {
    for (text <- message.text)
      api.request(SendMessage(message.sender, toL337(text)))
  }
}
