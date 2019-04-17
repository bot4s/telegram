import monix.eval.Task
import monix.eval.TaskApp

object Launcher extends TaskApp {

  override def run(args: Array[String]): Task[Unit] =
    args.toList match {
      case List("EchoBot", token) =>
        new EchoBot(token).run
      case List(name, _) =>
        Task.raiseError(new Exception(s"Unknown bot $name"))
      case _ =>
        Task.raiseError(new Exception("Usage:\nLauncher $botName $token"))
    }
}
