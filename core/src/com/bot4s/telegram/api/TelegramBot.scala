package com.bot4s.telegram.api

import scala.concurrent.ExecutionContext

trait TelegramBot extends BotBase with GlobalExecutionContext

trait BotExecutionContext {
  implicit val executionContext: ExecutionContext
}

trait GlobalExecutionContext extends BotExecutionContext {
  override implicit val executionContext: ExecutionContext = scala.concurrent.ExecutionContext.global
}
