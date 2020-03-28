package com.bot4s.telegram.future

import cats.MonadError
import cats.instances.future._
import com.bot4s.telegram.api.BotBase

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

trait TelegramBot extends BotBase[Future] with GlobalExecutionContext {
  val monad: MonadError[Future, Throwable] = MonadError[Future, Throwable]
}

trait BotExecutionContext {
  implicit val executionContext: ExecutionContext
}

trait GlobalExecutionContext extends BotExecutionContext {
  override implicit val executionContext: ExecutionContext =
    scala.concurrent.ExecutionContext.global
}
