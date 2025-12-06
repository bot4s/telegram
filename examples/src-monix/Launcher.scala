import cats.effect._
import monix.eval._
import sttp.client4.httpclient.monix.HttpClientMonixBackend

object Launcher extends TaskApp {

  def run(args: List[String]): Task[ExitCode] =
    args.toList match {
      case List("EchoBot", token) =>
        HttpClientMonixBackend().flatMap(b => new EchoBot(token, b).run()).as(ExitCode.Success)
      case List(name, _) =>
        Task.raiseError(new Exception(s"Unknown bot $name"))
      case _ =>
        Task.raiseError(new Exception("Usage:\nLauncher $botName $token"))
    }
}
