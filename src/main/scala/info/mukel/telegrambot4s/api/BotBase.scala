package info.mukel.telegrambot4s.api

import com.typesafe.scalalogging.Logger
import info.mukel.telegrambot4s.models._

import scala.concurrent.Future

/** Bare-bones bot callback-like interface, stackable.
  *
  * The token is declared as a non-val to avoid initialization conflicts.
  * For a val-like token consider the following:
  * {{
  *   lazy val token = ...
  * }}
  */
trait BotBase {
  def token: String
  val logger: Logger
  val client: RequestHandler

  def request = client

  /** Dispatch updates to specialized handlers.
    * Incoming update can be a message, inline query, callback query of inline result stat.
    *
    * @param u Incoming update.
    */
  def onUpdate(u: Update): Unit = {
    u.message.foreach(onMessage)
    u.inlineQuery.foreach(onInlineQuery)
    u.callbackQuery.foreach(onCallbackQuery)
    u.chosenInlineResult.foreach(onChosenInlineResult)
    u.channelPost.foreach(onChannelPost)
    u.editedChannelPost.foreach(onEditedChannelPost)
  }

  def onMessage(message: Message): Unit = {}
  def onInlineQuery(inlineQuery: InlineQuery): Unit = {}
  def onCallbackQuery(callbackQuery: CallbackQuery): Unit = {}
  def onChosenInlineResult(chosenInlineResult: ChosenInlineResult): Unit = {}

  def onChannelPost(message: Message): Unit = {}
  def onEditedChannelPost(message: Message): Unit = {}

  def run(): Unit
  def shutdown(): Future[_]
}
