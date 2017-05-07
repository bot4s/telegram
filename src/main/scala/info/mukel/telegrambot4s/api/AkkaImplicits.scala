package info.mukel.telegrambot4s.api

import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

trait AkkaImplicits {
  implicit val system: ActorSystem //  = ActorSystem()
  implicit val materializer: ActorMaterializer // = ActorMaterializer()
  implicit val ec: ExecutionContext //  = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(2))
}

trait AkkaDefaults extends AkkaImplicits {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(2))
}
