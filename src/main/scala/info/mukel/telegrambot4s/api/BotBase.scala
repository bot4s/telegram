package info.mukel.telegrambot4s.api

import com.typesafe.scalalogging.{LazyLogging, Logger}
import info.mukel.telegrambot4s.models._

import scala.concurrent.ExecutionContext

/**
  */
trait BotBase {
  def token: String
  val logger: Logger
  val api: RequestHandler

  /** Dispatch updates to specialized handlers.
    * Incoming update can be a message, inline query, callback query of inline result stat.
    *
    * @param u Incoming update.
    */
  def onUpdate(u: Update): Unit = {
    u.message foreach (onMessage)
    u.inlineQuery foreach (onInlineQuery)
    u.callbackQuery foreach (onCallbackQuery)
    u.chosenInlineResult foreach (onChosenInlineResult)
  }

  def onMessage(message: Message): Unit = {}
  def onInlineQuery(inlineQuery: InlineQuery): Unit = {}
  def onCallbackQuery(callbackQuery: CallbackQuery): Unit = {}
  def onChosenInlineResult(chosenInlineResult: ChosenInlineResult): Unit = {}

  def run(): Unit
  def shutdown(): Unit
}
