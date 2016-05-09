package info.mukel.telegram.bots.v2

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import info.mukel.telegram.bots.v2.api.TelegramApiAkka
import info.mukel.telegram.bots.v2.model._

/**
  * Telegram Bot base
  */
trait TelegramBot {

  val token: String
  val api: TelegramApiAkka

  /*val updates: Source[Update, NotUsed]*/

  def handleUpdate(update: Update): Unit = {
    println(update)
    update.message foreach (handleMessage)
    update.inlineQuery foreach (handleInlineQuery)
    update.callbackQuery foreach (handleCallbackQuery)
    update.chosenInlineResult foreach (handleChosenInlineResult)
  }

  def handleMessage(message: Message): Unit = {}
  def handleInlineQuery(inlineQuery: InlineQuery): Unit = {}
  def handleCallbackQuery(callbackQuery: CallbackQuery): Unit = {}
  def handleChosenInlineResult(chosenInlineResult: ChosenInlineResult): Unit = {}

  def run(): Unit
  //def stop(): Unit
}
