package info.mukel.telegram.bots.v2.examples

import java.net.URLEncoder

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, StatusCode, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.ByteString
import info.mukel.telegram.bots.v2.api.Implicits._
import info.mukel.telegram.bots.v2.methods._
import info.mukel.telegram.bots.v2.model._
import info.mukel.telegram.bots.v2.{Commands, Polling}

/** Text-to-speech bot (using Google TTS API)
  *
  * Google will rightfully block your IP in case of abuse.
  */
object TTSBot extends TestBot with Polling with Commands {

  val ttsApiBase = "http://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&tl=en-us&q="

  on("/speak") { implicit message => args =>

    val text = args mkString " "
    val url = ttsApiBase + URLEncoder.encode(text, "UTF-8")

    for {
      response <- Http().singleRequest(HttpRequest(uri = Uri(url)))
      if response.status.isSuccess()
      bytes <-  Unmarshal(response).to[ByteString]
    } /* do */ {
      val voiceMp3 = InputFile.FromByteString("voice.mp3", bytes)
      api.request(SendVoice(message.sender, voiceMp3))
    }
  }
}
