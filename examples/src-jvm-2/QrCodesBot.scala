import java.net.URLEncoder

import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.http.scaladsl.model.{ HttpRequest, Uri }
import org.apache.pekko.http.scaladsl.unmarshalling.Unmarshal
import org.apache.pekko.util.ByteString
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.api._
import com.bot4s.telegram.future.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models.PekkoInputFile

import scala.concurrent.Future

/**
 * Generates QR codes from text/url.
 */
class QrCodesBot(token: String)
    extends PekkoExampleBot(token)
    with Polling
    with Commands[Future]
    with ChatActions[Future] {

  // Multiple variants
  onCommand("qr" | "qrcode" | "qr_code") { implicit msg =>
    withArgs { args =>
      val url = "https://api.qrserver.com/v1/create-qr-code/?data=" +
        URLEncoder.encode(args mkString " ", "UTF-8")

      for {
        response <- Http().singleRequest(HttpRequest(uri = Uri(url)))
        if response.status.isSuccess()
        bytes <- Unmarshal(response).to[ByteString]
        photo  = PekkoInputFile("qrcode.png", bytes)
        _     <- uploadingPhoto // Hint the user
        _     <- request(SendPhoto(msg.source, photo))
      } yield ()
    }
  }
}
