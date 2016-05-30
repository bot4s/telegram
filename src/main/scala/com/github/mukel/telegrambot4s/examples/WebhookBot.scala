package com.github.mukel.telegrambot4s.examples

import com.github.mukel.telegrambot4s._, api._, methods._, models._, Implicits._

/**
  * Seamless webhooks
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
