import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.IOApp
import sttp.client4.httpclient.cats.HttpClientCatsBackend

object Launcher extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    args match {
      case List("EchoBot", token) =>
        HttpClientCatsBackend.resource[IO]().use(c => new EchoBot[IO](token, c).startPolling()).as(ExitCode.Success)
      case List("CommandsBot", token) =>
        HttpClientCatsBackend.resource[IO]().use(c => new CommandsBot[IO](token, c).startPolling()).as(ExitCode.Success)
      case List(name, _) =>
        IO.raiseError(new Exception(s"Unknown bot $name"))
      case _ =>
        IO.raiseError(new Exception("Usage:\nLauncher $botName $token"))
    }
}
