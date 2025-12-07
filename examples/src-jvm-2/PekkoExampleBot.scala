import com.bot4s.telegram.api.PekkoTelegramBot
import com.bot4s.telegram.clients.PekkoHttpClient

abstract class PekkoExampleBot(val token: String) extends PekkoTelegramBot {
  override val client = new PekkoHttpClient(token)
}
