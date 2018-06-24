import info.mukel.telegrambot4s.api.declarative.Commands
import info.mukel.telegrambot4s.api.{Polling, _}
import info.mukel.telegrambot4s.methods._
import info.mukel.telegrambot4s.models.InputFile

import scala.concurrent.Future

/**
  * Such Telegram, many bots, so Dogesome.
  */
class DogeBot(token: String) extends ExampleBot(token)
  with Polling
  with Commands
  with ChatActions {

  onCommand("/doge") { implicit msg =>
    withArgs { args =>
      val url = "http://dogr.io/" + (args mkString "/") + ".png?split=false"
      for {
        res <- Future { scalaj.http.Http(url).asBytes }
        if res.isSuccess
        bytes = res.body
      } /* do */ {
        println(bytes.length)
        val photo = InputFile("doge.png", bytes)
        uploadingPhoto // Hint the user
        request(SendPhoto(msg.source, photo))
      }
    }
  }
}
