package info.mukel.telegrambot4s.api

import com.typesafe.scalalogging.Logger
import info.mukel.telegrambot4s.models.UpdateType.UpdateType
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

  // Allow all updates by default
  def allowedUpdates: Option[Seq[UpdateType]] = None

  /** Dispatch updates to specialized handlers.
    * Incoming update can be a message, inline query, callback query of inline result stat.
    *
    * @param u Incoming update.
    */
  def onUpdate(u: Update): Unit = {
    u.message.foreach(onMessage)
    u.editedMessage.foreach(onEditedMessage)

    u.channelPost.foreach(onChannelPost)
    u.editedChannelPost.foreach(onEditedChannelPost)

    u.inlineQuery.foreach(onInlineQuery)
    u.chosenInlineResult.foreach(onChosenInlineResult)

    u.callbackQuery.foreach(onCallbackQuery)

    u.shippingQuery.foreach(onShippingQuery)
    u.preCheckoutQuery.foreach(onPreCheckoutQuery)
  }

  def onMessage(message: Message): Unit = {}
  def onEditedMessage(editedMessage: Message): Unit = {}

  def onChannelPost(message: Message): Unit = {}
  def onEditedChannelPost(message: Message): Unit = {}

  def onInlineQuery(inlineQuery: InlineQuery): Unit = {}
  def onChosenInlineResult(chosenInlineResult: ChosenInlineResult): Unit = {}

  def onCallbackQuery(callbackQuery: CallbackQuery): Unit = {}

  def onShippingQuery(shippingQuery: ShippingQuery): Unit = {}
  def onPreCheckoutQuery(preCheckoutQuery: PreCheckoutQuery): Unit = {}

  def run(): Unit = {}
  def shutdown(): Future[Unit] = { Future.successful(()) }
}
