

import java.net.URLEncoder

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.ByteString
import info.mukel.telegrambot4s.akka.api.AkkaDefaults
import info.mukel.telegrambot4s.akka.models.AkkaInputFile
import info.mukel.telegrambot4s.api.declarative.Commands
import info.mukel.telegrambot4s.api.{Polling, _}
import info.mukel.telegrambot4s.methods._

/**
  * Generates QR codes from text/url.
  */
class QrCodesBot(token: String) extends ExampleBot(token)
  with Polling
  with AkkaDefaults
  with Commands
  with ChatActions {

  // Multiple variants
  onCommand('qr | 'qrcode | 'qr_code) { implicit msg =>
    withArgs { args =>
      val url = "https://api.qrserver.com/v1/create-qr-code/?data=" +
        URLEncoder.encode(args mkString " ", "UTF-8")

      for {
        response <- Http().singleRequest(HttpRequest(uri = Uri(url)))
        if response.status.isSuccess()
        bytes <- Unmarshal(response).to[ByteString]
      } /* do */ {
        val photo = AkkaInputFile("qrcode.png", bytes)
        uploadingPhoto // Hint the user
        request(SendPhoto(msg.source, photo))
      }
    }
  }
}