package com.bot4s.telegram.api

import cats.MonadError
import cats.implicits._
import com.bot4s.telegram.future.BotExecutionContext
import com.bot4s.telegram.log.StrictLogging

import scala.concurrent.{ExecutionContext, Future}

abstract class TestBot
    extends BotBase[Future]
    with StrictLogging
    with BotExecutionContext {

  override lazy val client: RequestHandler[Future] = ???

  override implicit def monad: MonadError[Future, Throwable] =
    MonadError[Future, Throwable]
}
