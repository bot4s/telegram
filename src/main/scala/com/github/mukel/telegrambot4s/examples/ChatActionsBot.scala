package com.github.mukel.telegrambot4s.examples

import info.mukel.telegram.bots.v2.{ChatActions, Commands, Polling}

/**
  * Created by mukel on 5/17/16.
  */
object ChatActionsBot extends TestBot with Polling with Commands with ChatActions {
  on("/take_your_time") { implicit message => _ =>
    typing // Warn the user a long waiting time
    Thread.sleep(5000) // some looong operation
    reply("Job done")
  }
}
