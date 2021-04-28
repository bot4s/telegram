import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.clients.FutureSttpClient
import com.bot4s.telegram.future.TelegramBot

import scala.concurrent.Future

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

  implicit val backend                        = SttpBackends.default
  override val client: RequestHandler[Future] = new FutureSttpClient(token)
}
