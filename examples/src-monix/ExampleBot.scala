import com.bot4s.telegram.cats.TelegramBot
import com.softwaremill.sttp.asynchttpclient.monix.AsyncHttpClientMonixBackend

abstract class ExampleBot(val token: String)
  extends TelegramBot(token, AsyncHttpClientMonixBackend())
