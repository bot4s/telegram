package info.mukel.telegrambot4s.akka.api

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

trait AkkaImplicits {
  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer
}
