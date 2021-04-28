import cats.effect.{ Concurrent, ContextShift }
import com.bot4s.telegram.cats.TelegramBot
import org.asynchttpclient.Dsl.asyncHttpClient
import sttp.client3.asynchttpclient.cats.AsyncHttpClientCatsBackend

abstract class ExampleBot[F[_]: ContextShift: Concurrent](val token: String)
    extends TelegramBot[F](token, AsyncHttpClientCatsBackend.usingClient[F](asyncHttpClient()))
