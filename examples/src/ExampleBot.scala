import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.clients.FutureSttpClient
import com.bot4s.telegram.future.TelegramBot

import scala.concurrent.Future
import sttp.client3.SttpBackend

/**
 * Quick helper to spawn example bots.
 *
 * Mix Polling or Webhook accordingly.
 *
 * Example:
 * new EchoBot("123456789:qwertyuiopasdfghjklyxcvbnm123456789").run()
 *
 * @param token Bot's token.
 */
abstract class ExampleBot(val token: String) extends TelegramBot {

  implicit val backend: SttpBackend[Future, Any] = SttpBackends.default
  override lazy val client: RequestHandler[Future]    = new FutureSttpClient(token)
}
