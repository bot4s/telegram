package com.bot4s.telegram.api

import cats.MonadError
import cats.instances.list._
import cats.syntax.foldable._

import com.bot4s.telegram.models.UpdateType.UpdateType
import com.bot4s.telegram.models._

/**
 * Skeleton for Telegram bots.
 */
trait BotBase[F[_]] {
  implicit val monad: MonadError[F, Throwable]
  val client: RequestHandler[F]

  def request: RequestHandler[F] = client

  /**
   * Allowed updates. See [[UpdateType.Filters]].
   * By default all updates are allowed.
   *
   * @return Allowed updates. `None` indicates no-filtering (all updates allowed).
   *
   * {{{
   *   import UpdateType.Filters._
   *   override def allowedUpdates: Option[Seq[UpdateType]] =
   *     Some(MessageUpdates ++ InlineUpdates)
   * }}}
   */
  def allowedUpdates: Option[Seq[UpdateType]] = None

  /**
   * Dispatch updates to specialized handlers.
   * Incoming update can be a message, edited message, channel post, edited channel post,
   * inline query, inline query results (sample), callback query, shipping or pre-checkout events.
   *
   * @param u Incoming update.
   */
  def receiveUpdate(u: Update, botUser: Option[User]): F[Unit] =
    List(
      u.message.map(receiveMessage _),
      u.editedMessage.map(receiveEditedMessage _),
      u.message.map(m => receiveExtMessage((m, botUser))),
      u.channelPost.map(receiveChannelPost _),
      u.editedChannelPost.map(receiveEditedChannelPost _),
      u.inlineQuery.map(receiveInlineQuery _),
      u.chosenInlineResult.map(receiveChosenInlineResult _),
      u.callbackQuery.map(receiveCallbackQuery _),
      u.shippingQuery.map(receiveShippingQuery _),
      u.preCheckoutQuery.map(receivePreCheckoutQuery _)
    ).flatten.sequence_

  protected lazy val unit = monad.pure(())

  def receiveMessage(message: Message): F[Unit]                       = unit
  def receiveEditedMessage(editedMessage: Message): F[Unit]           = unit
  def receiveExtMessage(extMessage: (Message, Option[User])): F[Unit] = unit

  def receiveChannelPost(message: Message): F[Unit]       = unit
  def receiveEditedChannelPost(message: Message): F[Unit] = unit

  def receiveInlineQuery(inlineQuery: InlineQuery): F[Unit]                      = unit
  def receiveChosenInlineResult(chosenInlineResult: ChosenInlineResult): F[Unit] = unit

  def receiveCallbackQuery(callbackQuery: CallbackQuery): F[Unit] = unit

  def receiveShippingQuery(shippingQuery: ShippingQuery): F[Unit]          = unit
  def receivePreCheckoutQuery(preCheckoutQuery: PreCheckoutQuery): F[Unit] = unit

  def run(): F[Unit] = unit
  def shutdown(): Unit = {}
}
