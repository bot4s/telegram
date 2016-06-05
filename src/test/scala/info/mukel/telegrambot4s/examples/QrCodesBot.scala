package info.mukel.telegrambot4s.examples

import java.net.URLEncoder

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
  * Generates QR codes from text/url.
  */
object QrCodesBot extends TestBot with Polling with Commands with ChatActions {
  on("/qr") { implicit message => args =>
    val url = "https://api.qrserver.com/v1/create-qr-code/?data=" +
      URLEncoder.encode(args mkString " ", "UTF-8")
    for {
      response <- Http().singleRequest(HttpRequest(uri = Uri(url)))
      if response.status.isSuccess()
      bytes <-  Unmarshal(response).to[ByteString]
    } /* do */ {
      val photo = InputFile.FromByteString("qrcode.png", bytes)
      uploadingPhoto // hint the user
      api.request(SendPhoto(message.sender, photo))
    }
  }
}