import com.bot4s.telegram.api.AkkaTelegramBot
import com.bot4s.telegram.clients.AkkaHttpClient
import slogging.{LogLevel, LoggerConfig, PrintLoggerFactory}

abstract class AkkaExampleBot(val token: String) extends AkkaTelegramBot {
  LoggerConfig.factory = PrintLoggerFactory()
  // set log level, e.g. to TRACE
  LoggerConfig.level = LogLevel.TRACE

  override val client = new AkkaHttpClient(token)
}
