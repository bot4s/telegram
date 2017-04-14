package info.mukel.telegrambot4s.examples

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.ByteString
import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.methods._
import info.mukel.telegrambot4s.models._

/**
  * Such Telegram, many bots, so Dogesome.
  */
class DogeBot(token: String)
  extends TestBot(token) with Polling with Commands with ChatActions {

  on("/doge", "generates doge meme with given terms") { implicit msg =>
    args =>
      val url = "http://dogr.io/" + (args mkString "/") + ".png?split=false"

      for {
        res <- Http().singleRequest(HttpRequest(uri = Uri(url)))
        if res.status.isSuccess()
        bytes <- Unmarshal(res).to[ByteString]
      } /* do */ {
        val photo = InputFile("doge.png", bytes)
        uploadingPhoto // Hint the user
        request(SendPhoto(msg.source, photo))
      }
  }
}
