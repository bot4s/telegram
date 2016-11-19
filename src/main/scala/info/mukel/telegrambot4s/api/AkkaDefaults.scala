package info.mukel.telegrambot4s.api

import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

/**
  * Created by mukel on 19.11.16.
  */
trait AkkaDefaults {
  implicit val system = ActorSystem("telegram-bot")
  implicit val materializer = ActorMaterializer()
  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(2))
}
