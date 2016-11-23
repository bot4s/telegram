package info.mukel.telegrambot4s.api

import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

trait AkkaDefaults {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(2))
}
