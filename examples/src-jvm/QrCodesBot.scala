import java.net.URLEncoder

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.ByteString
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.api.{Polling, _}
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models.AkkaInputFile

import scala.concurrent.Future

/**
  * Generates QR codes from text/url.
  */
class QrCodesBot(token: String) extends AkkaExampleBot(token)
  with Polling[Future]
  with Commands[Future]
  with ChatActions[Future] {

  // Multiple variants
  onCommand('qr | 'qrcode | 'qr_code) { implicit msg =>
    withArgs { args =>
      val url = "https://api.qrserver.com/v1/create-qr-code/?data=" +
        URLEncoder.encode(args mkString " ", "UTF-8")

      for {
        response <- Http().singleRequest(HttpRequest(uri = Uri(url)))
        if response.status.isSuccess()
        bytes <- Unmarshal(response).to[ByteString]
        photo = AkkaInputFile("qrcode.png", bytes)
        _ <- uploadingPhoto // Hint the user
        _ <- request(SendPhoto(msg.source, photo))
      } yield ()
    }
  }
}
