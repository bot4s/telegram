package info.mukel.telegrambot4s.examples

import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.{LazyLogging, Logger, StrictLogging}
import info.mukel.telegrambot4s.api.{BotBase, RequestHandler, TelegramApiAkka}
import info.mukel.telegrambot4s.methods.ApiRequest

import scala.concurrent.{ExecutionContext, Future, Promise}

/**
  * Created by mukel on 18.11.16.
  */
trait TestEnvironment extends StrictLogging {
  _ : BotBase =>
}
