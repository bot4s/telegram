package com.bot4s.telegram.serverless

import com.bot4s.telegram.api.BotBase
import com.bot4s.telegram.marshalling
import com.bot4s.telegram.models.Update

import scala.concurrent.{ ExecutionContext, Future }
import scala.scalajs.js
import scala.scalajs.js.JSConverters._

object Serverless {
  def handlePayload(
    bot: BotBase[Future],
    updateType: String,
    payload: js.Any,
    ctx: js.UndefOr[ServerlessContext] = js.undefined
  )(implicit ec: ExecutionContext): js.Promise[Unit] =
    bot.receiveUpdate(updateFrom(updateType, payload, ctx), None).toJSPromise

  private def updateFrom(updateType: String, payload: js.Any, ctx: js.UndefOr[ServerlessContext]): Update =
    rawUpdate(ctx).fold(syntheticUpdate(updateType, payload))(decodeUpdate)

  private def rawUpdate(ctx: js.UndefOr[ServerlessContext]): Option[js.Any] =
    if (js.isUndefined(ctx)) {
      None
    } else {
      val update = ctx.asInstanceOf[ServerlessContext].update
      if (js.isUndefined(update)) None else Some(update.asInstanceOf[js.Any])
    }

  private def syntheticUpdate(updateType: String, payload: js.Any): Update = {
    val payloadJson = js.JSON.stringify(payload)
    marshalling.fromJson[Update](s"""{"update_id":0,"$updateType":$payloadJson}""")
  }

  private def decodeUpdate(value: js.Any): Update =
    marshalling.fromJson[Update](js.JSON.stringify(value))
}
