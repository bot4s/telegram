package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.clients.ScalajHttpClient

import scala.concurrent.ExecutionContext

/** Telegram Bot with "sane" defaults.
  */
trait TelegramBot extends BotBase with GlobalExecutionContext {
  override val client: RequestHandler = new ScalajHttpClient(token)
}

trait BotExecutionContext {
  implicit val executionContext: ExecutionContext
}

trait GlobalExecutionContext extends BotExecutionContext {
  implicit val executionContext: ExecutionContext = scala.concurrent.ExecutionContext.global
}
