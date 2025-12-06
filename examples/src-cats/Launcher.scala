import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.IOApp
import sttp.client4.httpclient.fs2.HttpClientFs2Backend
import cats.effect.Blocker

object Launcher extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    Blocker[IO].use { blocker =>
      args match {
        case List("EchoBot", token) =>
          HttpClientFs2Backend
            .resource[IO](blocker)
            .use(backend => new EchoBot[IO](token, backend).startPolling())
            .as(ExitCode.Success)
        case List("CommandsBot", token) =>
          HttpClientFs2Backend
            .resource[IO](blocker)
            .use(backend => new CommandsBot[IO](token, backend).startPolling())
            .as(ExitCode.Success)
        case List(name, _) =>
          IO.raiseError(new Exception(s"Unknown bot $name"))
        case _ =>
          IO.raiseError(new Exception("Usage:\nLauncher $botName $token"))
      }
    }
}
