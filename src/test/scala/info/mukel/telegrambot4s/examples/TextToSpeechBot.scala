package info.mukel.telegrambot4s.examples

import java.net.URLEncoder

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.ByteString
import info.mukel.telegrambot4s._, api._, methods._, models._, Implicits._

/** Text-to-speech bot (using Google TTS API)
  *
  * Google will rightfully block your IP in case of abuse.
  */
object TextToSpeechBot extends TestBot with Polling with Commands with ChatActions {
  val ttsApiBase = "http://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&tl=en-us&q="
  on("/speak") { implicit msg => args =>
    val text = args mkString " "
    val url = ttsApiBase + URLEncoder.encode(text, "UTF-8")
    for {
      response <- Http().singleRequest(HttpRequest(uri = Uri(url)))
      if response.status.isSuccess()
      bytes <-  Unmarshal(response).to[ByteString]
    } /* do */ {
      uploadingAudio // hint the user
      val voiceMp3 = InputFile.FromByteString("voice.mp3", bytes)
      api.request(SendVoice(msg.sender, voiceMp3))
    }
  }
}
