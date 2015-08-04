
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
      sendPhoto(sender, new File("./Mukel_Photo.jpg"), Some("It's me!!!"))
    }
  }

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
