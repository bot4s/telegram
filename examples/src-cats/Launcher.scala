import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.IOApp

object Launcher extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    args match {
      case List("EchoBot", token) =>
        new EchoBot[IO](token).startPolling.map(_ => ExitCode.Success)
      case List("CommandsBot", token) =>
        new CommandsBot[IO](token).startPolling.map(_ => ExitCode.Success)
      case List(name, _) =>
        IO.raiseError(new Exception(s"Unknown bot $name"))
      case _ =>
        IO.raiseError(new Exception("Usage:\nLauncher $botName $token"))
    }
}
