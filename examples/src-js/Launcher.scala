import scala.scalajs.js

object Launcher {
  def main(args: Array[String]): Unit = {
    val env = js.Dynamic.global.process.env
    val token = env.BOT_TOKEN
      .asInstanceOf[js.UndefOr[String]]
      .toOption
      .filter(_.nonEmpty)
      .getOrElse(throw new IllegalArgumentException("Set BOT_TOKEN to your Telegram bot token"))
    val name = env.BOT_NAME.asInstanceOf[js.UndefOr[String]].getOrElse("EchoBot")
    val bot = name match {
      case "EchoBot"   => new EchoBot(token)
      case "RandomBot" => new RandomBot(token)
      case _           => throw new IllegalArgumentException("BOT_NAME must be EchoBot or RandomBot")
    }

    import bot.executionContext

    println(s"Starting $name on Node.js; press Ctrl+C to stop")
    bot.run().failed.foreach { error =>
      // HTTP errors may contain the request URL, including the token.
      Console.err.println(s"Bot failed: ${error.toString.replace(token, "<redacted>")}")
      js.Dynamic.global.process.exitCode = 1
    }
  }
}
