import com.bot4s.telegram.monix.TelegramBot

abstract class ExampleBot(val token: String)
  extends TelegramBot(token, "api.telegram.org", com.softwaremill.sttp.SttpBackendOptions.socksProxy("localhost", 9050))
