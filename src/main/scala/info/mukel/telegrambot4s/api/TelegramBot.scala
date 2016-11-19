package info.mukel.telegrambot4s.api

import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream.ActorMaterializer
import info.mukel.telegrambot4s.models._

import scala.concurrent.ExecutionContext

/** Telegram Bot base
  */
trait TelegramBot extends BotBase with AkkaDefaults with LiveEnvironment
