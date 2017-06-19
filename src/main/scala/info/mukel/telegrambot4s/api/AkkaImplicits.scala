package info.mukel.telegrambot4s.api

import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

trait AkkaImplicits {
  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer
  implicit val ec: ExecutionContext
}

trait AkkaDefaults extends AkkaImplicits {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  private val availableProcessors = Runtime.getRuntime.availableProcessors()

  implicit val ec: ExecutionContext =
    ExecutionContext.fromExecutor(Executors.newFixedThreadPool(availableProcessors * 2 + 1))
}
