import com.bot4s.telegram.api.AkkaTelegramBot
import com.bot4s.telegram.clients.AkkaHttpClient

abstract class AkkaExampleBot(val token: String) extends AkkaTelegramBot {
  override lazy val client = new AkkaHttpClient(token)
}
