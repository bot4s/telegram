import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main {
  def main(args: Array[String]): Unit = {
    val bot = new SelfHosted2048Bot("211113992:AAFGyBTCb9D4MLLIYG8RKtTnQRkczVJ9t4I",  "https://66e9bd2f.ngrok.io")
    val eol = bot.run()
    readLine()
    bot.shutdown()
    Await.result(eol, Duration.Inf)
  }
}