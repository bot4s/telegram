package info.mukel.telegram.bots.v2.examples

import java.net.URLEncoder

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import akka.util.ByteString
import info.mukel.telegram.bots.v2.api.Implicits._
import info.mukel.telegram.bots.v2.methods.{SendMessage, SendPhoto}
import info.mukel.telegram.bots.v2.model._
import info.mukel.telegram.bots.v2.{Commands, Polling}

/**
  * Created by mukel on 5/9/16.
  *
  * Generates QR codes from text.
  */
object QrBot extends TestBot with Polling with Commands {
  on("/qr") { implicit message => args =>
    val url = "https://api.qrserver.com/v1/create-qr-code/?data=" +
      URLEncoder.encode(args mkString " ", "UTF-8")
    for {
      response <- Http().singleRequest(HttpRequest(uri = Uri(url)))
      bytes <-  Unmarshal(response).to[ByteString]
    } /* do */ {
      val photo = InputFile.FromByteString("qrcode.png", bytes)
      api.request(SendPhoto(message.sender, photo))
    }
  }

}