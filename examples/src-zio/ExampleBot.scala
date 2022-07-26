import com.bot4s.telegram.cats.TelegramBot
import org.asynchttpclient.Dsl.asyncHttpClient
import zio.Task
import zio.interop.catz._
import sttp.client3.asynchttpclient.zio.AsyncHttpClientZioBackend

abstract class ExampleBot(val token: String)
    extends TelegramBot[Task](token, AsyncHttpClientZioBackend.usingClient(zio.Runtime.default, asyncHttpClient()))
