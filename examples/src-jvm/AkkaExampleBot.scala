import info.mukel.telegrambot4s.api.AkkaTelegramBot
import info.mukel.telegrambot4s.api.{RequestHandler, TelegramBot}
import slogging.{LogLevel, LoggerConfig, PrintLoggerFactory}
import info.mukel.telegrambot4s.clients.AkkaHttpClient

abstract class AkkaExampleBot(val token: String) extends AkkaTelegramBot {
  LoggerConfig.factory = PrintLoggerFactory()
  // set log level, e.g. to TRACE
  LoggerConfig.level = LogLevel.TRACE

  override val client: RequestHandler = new AkkaHttpClient(token)
}
