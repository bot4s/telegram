import java.io.{FileInputStream, InputStream}
import java.net.URLEncoder
import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}
import scalaj.http._
import java.io.File
import java.nio.file.{Files, Paths}

object FlunkeyBot extends SimpleBot(Utils.tokenFromFile("./flunkeybot.token")) with Commands {

  import ExecutionContext.Implicits.global

  on("hello") { (sender, args) =>
    replyTo(sender) {
      "Hello World!"
    }
  }

  // Async reply
  on("photo") { (sender, args) => Future {
    setStatus(sender, Status.UploadPhoto)
    Thread.sleep(5000)
    val file =new File("./Mukel_Photo.jpg")
    println("Doest the file exists: " + file.exists())
    sendPhoto(sender, file, Some("It's me!!!"))
  }}

  on("echo") { (sender, args) => Future {
    replyTo(sender) {
      args mkString " "
    }
  }}

  // Let Me Google That For You :)
  on("lmgtfy") { (sender, args) =>
    replyTo(sender, disable_web_page_preview = Some(true)) {
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
    FlunkeyBot.run()
  }
}
