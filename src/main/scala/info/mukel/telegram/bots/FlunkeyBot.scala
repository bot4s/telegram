package info.mukel.telegram.bots

import java.net.URLEncoder

import info.mukel.telegram.bots.OptionPimps._
import info.mukel.telegram.bots.api.{InputFile, ForceReply, ReplyKeyboardHide, ReplyKeyboardMarkup}

object FlunkeyBot extends TelegramBot(Utils.tokenFromFile("./tokens/flunkeybot.token")) with Polling with Commands {

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
    replyTo(sender, disableWebPagePreview = true) {
      "http://lmgtfy.com/?q=" + URLEncoder.encode(args mkString " ", "UTF-8")
  }}

  on("start") { (sender, _) =>
    replyTo(sender) {
      """/hello - says hello to the world!!!
        |/lmgtfy query - sends a LMGTFY URL !!
        |/echo args - simple echo
        |/photo - sends a picture of me!!!
      """.stripMargin
    }
  }

  on("video") { (sender, _) =>
    sendVideo(sender, InputFile("./files/video.mp4"),
      caption = "Sample video")
  }

  on("audio") { (sender, _) =>
    sendAudio(sender, InputFile("./files/audio.mp3"))
  }

  on("photo") { (sender, _) =>
    sendPhoto(sender, InputFile("./files/photo.jpg"),
      caption = "Bender the great!")
  }

  on("sticker") { (sender, _) =>
    sendSticker(sender, stickerFile = InputFile("./files/sticker.png"))
  }

  on("document") { (sender, _) =>
    sendDocument(sender, documentFile = InputFile("./files/document.pdf"))
  }

  on("markup") { (sender, _) =>
    val kb: Array[Array[String]] = Array(Array("Sucks!", "Sucks^2!"))
    val markup = ReplyKeyboardMarkup(kb,
      resizeKeyboard = true,
      oneTimeKeyboard = false)

    sendMessage(sender, "Bieber?", replyMarkup = markup)
  }

  on("hide") { (sender, _) =>
    val markup = ReplyKeyboardHide(true)
    sendMessage(sender, "Bla bla bla...", replyMarkup = markup)
  }

  on("forcereply") { (sender, _) =>
    val markup = ForceReply(true)
    sendMessage(sender, "Bla bla bla...", replyMarkup = markup)
  }
}

object Main {
  def main (args: Array[String]): Unit = {
    FlunkeyBot.run()
  }
}
