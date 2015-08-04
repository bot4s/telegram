
import java.io.{FileInputStream, InputStream}
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
}

object Main {
  def main (args: Array[String]): Unit = {
    FlunkeyBot.run()
  }
}
