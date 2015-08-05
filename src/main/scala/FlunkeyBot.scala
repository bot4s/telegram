import java.net.URLEncoder
import scala.concurrent.{ExecutionContext, Future}
import java.io.File

object FlunkeyBot extends SimpleBot(Utils.tokenFromFile("./flunkeybot.token")) with Commands {

  import ExecutionContext.Implicits.global
  import OptionPimps._

  on("hello") { (sender, args) =>
    replyTo(sender) {
      "Hello World!"
    }
  }

  // Async reply
  on("photo") { (sender, args) => Future {
    setStatus(sender, Status.UploadPhoto)
    val file = new File("./Mukel_Photo.jpg")
    sendPhoto(sender, file, Some("It's me!!!"))
  }}

  on("echo") { (sender, args) => Future {
    replyTo(sender) {
      args mkString " "
    }
  }}

  // Let Me Google That For You :)
  on("lmgtfy") { (sender, args) =>
    replyTo(sender, disable_web_page_preview = true) {
      "http://lmgtfy.com/?q=" + URLEncoder.encode(args mkString " ", "UTF-8")
  }}

  on("start") { (sender, args) =>
    replyTo(sender) {
      """/hello - says hello to the world!!!
        |/lmgtfy query - sends a LMGTFY URL !!
        |/echo args - simple echo
        |/photo - sends a picture of me!!!
      """.stripMargin
    }
  }
}

object Main {
  def main (args: Array[String]): Unit = {
    val t = new Thread(FlunkeyBot)
    t.setDaemon(true)
    t.start()
    t.join()
  }
}
