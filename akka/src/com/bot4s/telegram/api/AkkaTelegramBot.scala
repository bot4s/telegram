package com.bot4s.telegram.api

import akka.actor.ActorSystem
import com.bot4s.telegram.future.TelegramBot

trait AkkaTelegramBot extends TelegramBot with AkkaDefaults

trait AkkaImplicits {
  implicit val system: ActorSystem
}

trait AkkaDefaults extends AkkaImplicits {
  implicit val system: ActorSystem = ActorSystem()
}
