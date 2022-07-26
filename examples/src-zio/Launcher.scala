import zio._
import zio.{ ZIO, ZIOAppArgs }

object Launcher extends zio.ZIOAppDefault {

  override def run = ZIO.service[ZIOAppArgs].flatMap { args =>
    args.getArgs.toList match {
      case List("EchoBot", token) =>
        new EchoBot(token).startPolling().map(_ => ExitCode.success)
      case List("CommandsBot", token) =>
        new CommandsBot(token).startPolling().map(_ => ExitCode.success)
      case List(name, _) =>
        ZIO.fail(new Exception(s"Unknown bot $name"))
      case _ =>
        ZIO.fail(new Exception("Usage:\nLauncher $botName $token"))
    }
  }
}
