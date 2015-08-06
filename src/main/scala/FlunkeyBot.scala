import java.net.URLEncoder
import scala.concurrent.{ExecutionContext, Future}
import java.io.File

object FlunkeyBot extends PollingBot(Utils.tokenFromFile("./flunkeybot.token")) with Commands {

  import ExecutionContext.Implicits.global
  import OptionPimps._

  on("hello") { (sender, args) =>
    replyTo(sender) {
      "Hello World!"
    }
  }

  on("echo") { (sender, args) => {
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

  on("video") { (sender, args) =>
    sendVideo(sender, new File("./video.mp4"),
      caption = "Sample video")
  }

  on("audio") { (sender, args) =>
    sendAudio(sender, new File("./audio.mp3"))
  }

  on("photo") { (sender, args) =>
    sendPhoto(sender, new File("./photo.jpg"),
      caption = "Bender the great!")
  }

  on("sticker") { (sender, args) =>
    sendSticker(sender, sticker_file = new File("./sticker.png"))
  }

  on("document") { (sender, args) =>
    sendDocument(sender, document_file = new File("./document.pdf"))
  }
}

object Main {
  def main (args: Array[String]): Unit = {
    FlunkeyBot.run()
  }
}
