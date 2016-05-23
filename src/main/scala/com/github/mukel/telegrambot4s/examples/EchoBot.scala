package com.github.mukel.telegrambot4s.examples

import info.mukel.telegram.bots.v2.methods._
import info.mukel.telegram.bots.v2.model._
import info.mukel.telegram.bots.v2.api.Implicits._
import info.mukel.telegram.bots.v2.Polling

/**
  * Created by mukel on 5/9/16.
  */
object EchoBot extends TestBot with Polling {
  override def handleMessage(message: Message): Unit = {
    for (text <- message.text) {
      api.request(SendMessage(message.chat.id, text))
    }
  }
}
