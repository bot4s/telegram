import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.ByteString
import info.mukel.telegrambot4s.akka.api.AkkaDefaults
import info.mukel.telegrambot4s.akka.models.AkkaInputFile
import info.mukel.telegrambot4s.api.{Polling, _}
import info.mukel.telegrambot4s.api.declarative.Commands
import info.mukel.telegrambot4s.methods._
import info.mukel.telegrambot4s.models._

/**
  * Such Telegram, many bots, so Dogesome.
  */
class DogeBot(token: String) extends ExampleBot(token)
  with Polling
  with AkkaDefaults
  with Commands
  with ChatActions {

  onCommand("/doge") { implicit msg =>
    withArgs { args =>
      val url = "http://dogr.io/" + (args mkString "/") + ".png?split=false"

      for {
        res <- Http().singleRequest(HttpRequest(uri = Uri(url)))
        if res.status.isSuccess()
        bytes <- Unmarshal(res).to[ByteString]
      } /* do */ {
        val photo = AkkaInputFile("doge.png", bytes)
        uploadingPhoto // Hint the user
        request(SendPhoto(msg.source, photo))
      }
    }
  }
}
