package info.mukel.telegrambot4s.api

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import info.mukel.telegrambot4s.clients.AkkaHttpClient

trait AkkaTelegramBot extends BotBase with GlobalExecutionContext with AkkaDefaults {
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
