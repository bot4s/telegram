package info.mukel.telegram.bots.v2

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import info.mukel.telegram.bots.v2.api.TelegramApiAkka
import info.mukel.telegram.bots.v2.model._
import scala.concurrent.ExecutionContext
import java.util.concurrent.Executors

/**
  * Telegram Bot base
  */
trait TelegramBot {
  def token: String

  implicit val system = ActorSystem("telegram-bot")
  implicit val materializer = ActorMaterializer()
  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(2))

  val api = new TelegramApiAkka(token)

  def handleUpdate(update: Update): Unit = {
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
