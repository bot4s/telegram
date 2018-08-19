package com.bot4s.telegram.api

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.bot4s.telegram.clients.AkkaHttpClient

trait AkkaTelegramBot extends BotBase with GlobalExecutionContext with AkkaDefaults {
  def token: String
  override val client: RequestHandler = new AkkaHttpClient(token)
}

trait AkkaImplicits {
  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer
}

trait AkkaDefaults extends AkkaImplicits {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
}
