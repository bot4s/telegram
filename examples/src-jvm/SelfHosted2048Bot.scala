import akka.http.scaladsl.model.Uri
import akka.http.scaladsl.model.Uri.{Path, Query}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.bot4s.telegram.api.declarative.{Callbacks, Commands}
import com.bot4s.telegram.api.{AkkaDefaults, GameManager, Payload, Polling}
import com.bot4s.telegram.methods.SendGame

/**
  * 2048 self-hosted by the bot (from resources).
  *
  * To spawn the GameManager, a server is needed.
  * Otherwise use your favorite tunnel e.g. [[https://ngrok.com ngrok]]
  * Spawn the tunnel (and leave it running):
  * {{{
  *   ngrok http 8080
  * }}}
  *
  * Use the ngrok-provided address (e.g. https://e719813a.ngrok.io) as
  * your 'gameManagerHost'.
  *
  * The following endpoints should be linked to GameManager:
  * gameManagerHost/games/api/getScores
  * gameManagerHost/games/api/setScore
  *
  * @param token           Bot's token.
  * @param gameManagerHost Base URL of the game manager.
  */
class SelfHosted2048Bot(token: String, gameManagerHost: String)
  extends ExampleBot(token)
    with Polling
    with AkkaDefaults
    with Callbacks
    with GameManager
    with Commands {

  override val port: Int = 8080

  val Play2048 = "play_2048"

  onCommand(Play2048 or "2048" or "start") { implicit msg =>
    request(
      SendGame(msg.source, Play2048)
    )
  }

  onCallbackQuery { implicit cbq =>
    val acked = cbq.gameShortName.collect {
      case Play2048 =>
        val payload = Payload.forCallbackQuery(gameManagerHost)

        val url = Uri(gameManagerHost)
          .withPath(Path(s"/$Play2048/index.html"))
          .withQuery(Query("payload" -> payload.base64Encode))

        ackCallback(url = Some(url.toString()))
    }

    acked.getOrElse(ackCallback())
  }

  override def routes: Route =
    super.routes ~
      gameManagerRoute ~ {
      pathPrefix(Play2048) {
        getFromResourceDirectory(Play2048)
      }
    }
}
