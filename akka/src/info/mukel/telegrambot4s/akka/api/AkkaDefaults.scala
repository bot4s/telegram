package info.mukel.telegrambot4s.akka.api

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

trait AkkaDefaults extends AkkaImplicits {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
}
