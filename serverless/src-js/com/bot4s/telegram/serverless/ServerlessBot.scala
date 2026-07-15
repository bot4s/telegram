package com.bot4s.telegram.serverless

import cats.MonadError
import cats.instances.future.catsStdInstancesForFuture
import com.bot4s.telegram.api.{ BotBase, RequestHandler }
import com.bot4s.telegram.future.BotExecutionContext

import scala.concurrent.{ ExecutionContext, Future }
import scala.scalajs.concurrent.JSExecutionContext
import scala.scalajs.js

trait ServerlessBot extends BotBase[Future] with BotExecutionContext {
  override implicit val executionContext: ExecutionContext = JSExecutionContext.queue
  override implicit val monad: MonadError[Future, Throwable] = catsStdInstancesForFuture
  override val client: RequestHandler[Future] = new ServerlessRequestHandler()

  final def handlePayload(
    updateType: String,
    payload: js.Any,
    ctx: js.UndefOr[ServerlessContext] = js.undefined
  ): js.Promise[Unit] =
    Serverless.handlePayload(this, updateType, payload, ctx)

  final def handleMessage(payload: js.Any, ctx: js.UndefOr[ServerlessContext] = js.undefined): js.Promise[Unit] =
    handlePayload("message", payload, ctx)

  final def handleEditedMessage(payload: js.Any, ctx: js.UndefOr[ServerlessContext] = js.undefined): js.Promise[Unit] =
    handlePayload("edited_message", payload, ctx)

  final def handleChannelPost(payload: js.Any, ctx: js.UndefOr[ServerlessContext] = js.undefined): js.Promise[Unit] =
    handlePayload("channel_post", payload, ctx)

  final def handleEditedChannelPost(payload: js.Any, ctx: js.UndefOr[ServerlessContext] = js.undefined): js.Promise[Unit] =
    handlePayload("edited_channel_post", payload, ctx)

  final def handleInlineQuery(payload: js.Any, ctx: js.UndefOr[ServerlessContext] = js.undefined): js.Promise[Unit] =
    handlePayload("inline_query", payload, ctx)

  final def handleChosenInlineResult(payload: js.Any, ctx: js.UndefOr[ServerlessContext] = js.undefined): js.Promise[Unit] =
    handlePayload("chosen_inline_result", payload, ctx)

  final def handleCallbackQuery(payload: js.Any, ctx: js.UndefOr[ServerlessContext] = js.undefined): js.Promise[Unit] =
    handlePayload("callback_query", payload, ctx)

  final def handleShippingQuery(payload: js.Any, ctx: js.UndefOr[ServerlessContext] = js.undefined): js.Promise[Unit] =
    handlePayload("shipping_query", payload, ctx)

  final def handlePreCheckoutQuery(payload: js.Any, ctx: js.UndefOr[ServerlessContext] = js.undefined): js.Promise[Unit] =
    handlePayload("pre_checkout_query", payload, ctx)

  final def handleChatJoinRequest(payload: js.Any, ctx: js.UndefOr[ServerlessContext] = js.undefined): js.Promise[Unit] =
    handlePayload("chat_join_request", payload, ctx)
}
