import cats.effect.Async
import com.bot4s.telegram.cats.TelegramBot
import com.softwaremill.sttp.asynchttpclient.cats.AsyncHttpClientCatsBackend

abstract class ExampleBot[F[_]: Async](val token: String)
  extends TelegramBot(token, AsyncHttpClientCatsBackend())
