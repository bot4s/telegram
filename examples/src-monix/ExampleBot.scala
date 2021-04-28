import com.bot4s.telegram.cats.TelegramBot
import org.asynchttpclient.Dsl.asyncHttpClient
import sttp.client3.asynchttpclient.monix.AsyncHttpClientMonixBackend

abstract class ExampleBot(val token: String)
    extends TelegramBot(token, AsyncHttpClientMonixBackend.usingClient(asyncHttpClient()))
