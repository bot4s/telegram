package info.mukel.telegram.bots.v2

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import info.mukel.telegram.bots.v2.api.{TelegramApiAkka, TelegramApiScalaj}
import info.mukel.telegram.bots.v2.model.Update

/**
  * Telegram Bot base
  */
trait TelegramBot {
  val api: TelegramApiAkka
  val updates: Source[Update, NotUsed]
  def handleUpdate(update: Update): Unit

  def run(): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    updates.runForeach(handleUpdate)
  }
}
