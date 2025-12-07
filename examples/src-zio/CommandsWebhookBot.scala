import zio._
import zhttp.http._
import com.bot4s.telegram.api.declarative.{ Commands, RegexCommands }
import com.bot4s.telegram.methods.SetWebhook
import zhttp.service.Server
import zhttp.service.server.ServerChannelFactory
import zhttp.service.EventLoopGroup
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

  private def callback = Http.collectZIO[Request] { case req @ Method.POST -> !! =>
    for {
      body    <- req.bodyAsString
      update  <- ZIO.attempt(fromJson[Update](body))
      handler <- receiveUpdate(update, None)
    } yield Response.ok
  }

  private def server = Server.port(8081) ++ Server.app(callback)
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
      server.make
        .flatMap(start => ZIO.logInfo(s"Server started on ${start.port}") *> ZIO.never)
        .provide(ServerChannelFactory.auto, EventLoopGroup.auto(1), Scope.default)

  // String commands.
  onCommand("/hello") { implicit msg =>
    reply("Hello America!").ignore
  }

}
