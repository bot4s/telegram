package com.bot4s.telegram.api

import org.apache.pekko.actor.ActorSystem
import com.bot4s.telegram.future.TelegramBot

trait PekkoTelegramBot extends TelegramBot with PekkoDefaults

trait PekkoImplicit {
  implicit val system: ActorSystem
}

trait PekkoDefaults extends PekkoImplicit {
  implicit val system: ActorSystem = ActorSystem()
}
