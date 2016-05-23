package com.github.mukel.telegrambot4s.examples

import info.mukel.telegram.bots.v2.Polling
import info.mukel.telegram.bots.v2.methods.SendMessage
import info.mukel.telegram.bots.v2.model.Message
import info.mukel.telegram.bots.v2.api.Implicits._

/**
  * Created by mukel on 5/14/16.
  */
object KamikazeBot extends TestBot with Polling {

  override def run(): Unit = {
    super.run()
    api.request(SendMessage(0, ""))
  }

}
