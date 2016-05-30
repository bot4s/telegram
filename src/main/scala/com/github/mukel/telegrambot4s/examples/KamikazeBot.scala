package com.github.mukel.telegrambot4s.examples

import com.github.mukel.telegrambot4s._, api._, methods._, models._, Implicits._

/**
  * Created by mukel on 5/14/16.
  */
object KamikazeBot extends TestBot with Polling {

  override def run(): Unit = {
    super.run()
    api.request(SendMessage(0, ""))
  }

}
