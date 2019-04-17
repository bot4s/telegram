import cats.effect._
import cats.implicits._
import monix.eval._

object Launcher extends TaskApp {

  def run(args: List[String]): Task[ExitCode] =
    args.toList match {
      case List("EchoBot", token) =>
        new EchoBot(token).run.as(ExitCode.Success)
      case List(name, _) =>
        Task.raiseError(new Exception(s"Unknown bot $name"))
      case _ =>
        Task.raiseError(new Exception("Usage:\nLauncher $botName $token"))
    }
}
