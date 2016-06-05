package info.mukel.telegrambot4s.api

import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream.ActorMaterializer
import info.mukel.telegrambot4s.models._

import scala.concurrent.ExecutionContext

/** Telegram Bot base
  */
trait TelegramBot {
  def token: String

  implicit val system = ActorSystem("telegram-bot")
  implicit val materializer = ActorMaterializer()
  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(2))

  val log = Logging.getLogger(system, this)

  val api = new TelegramApiAkka(token)

  /**
    *
    * @param update
    */
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
