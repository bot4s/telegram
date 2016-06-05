package info.mukel.telegrambot4s.examples

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.ByteString
import com.github.mukel.telegrambot4s._
import info.mukel.telegrambot4s.api.{ChatActions, Commands, Polling}
import info.mukel.telegrambot4s.Implicits
import info.mukel.telegrambot4s.methods.SendPhoto
import info.mukel.telegrambot4s.models.InputFile

/**
  * Such Telegram, many bots, so Dogesome
  */
object DogeBot extends TestBot with Polling with Commands with ChatActions {
  on("/doge") { implicit message => args =>
    val url = "http://dogr.io/" + (args mkString "/") + ".png?split=false"
    for {
      response <- Http().singleRequest(HttpRequest(uri = Uri(url)))
      if response.status.isSuccess()
      bytes <-  Unmarshal(response).to[ByteString]
    } /* do */ {
      val photo = InputFile.FromByteString("doge.png", bytes)
      uploadingPhoto // hint the user
      api.request(SendPhoto(message.sender, photo))
    }
  }
}
