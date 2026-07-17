import zio._
import zio.http._
import com.bot4s.telegram.api.declarative.{ Commands, RegexCommands }
import com.bot4s.telegram.methods.SetWebhook
import com.bot4s.telegram.models.Update
import com.bot4s.telegram.cats.TelegramBot
import sttp.client4.Backend
import zio.interop.catz._

/**
 * Example on the usage of webhook with a custom webserver
 *
 * @param token Bot's token.
 * @param started Bot's current state (started or not)
 */
class CommandsWebhookBot(token: String, backend: Backend[Task], private val started: Ref.Synchronized[Boolean])
    extends TelegramBot(token, backend)
    with Commands[Task]
    with RegexCommands[Task] {

  import com.bot4s.telegram.marshalling._
  val webhookUrl = "https://XXXX.eu.ngrok.io"

  private def callback = Routes(
    Method.POST / Root -> handler { (req: Request) =>
      for {
        body    <- req.body.asString
        update  <- ZIO.attempt(fromJson[Update](body))
        handler <- receiveUpdate(update, None)
      } yield Response.ok
    }
  )

  private def server =
    Server
      .install(callback.sandbox)
      .flatMap(port => ZIO.logInfo(s"Server started on $port") *> ZIO.never)
      .provide(Server.defaultWithPort(8081))
  override def run() =
    started.updateZIO { isStarted =>
      for {
        _ <- ZIO.when(isStarted)(ZIO.fail(new Exception("Bot already started")))
        response <-
          request(SetWebhook(url = webhookUrl, certificate = None, allowedUpdates = None)).flatMap {
            case true => ZIO.succeed(true)
            case false =>
              ZIO.logError("Failed to set webhook")
              throw new RuntimeException("Failed to set webhook")
          }
      } yield response
    } *>
      server

  // String commands.
  onCommand("/hello") { implicit msg =>
    reply("Hello America!").ignore
  }

}
