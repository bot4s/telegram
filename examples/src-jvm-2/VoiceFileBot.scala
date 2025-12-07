import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.http.scaladsl.model.{ HttpRequest, Uri }
import org.apache.pekko.http.scaladsl.unmarshalling.Unmarshal
import org.apache.pekko.util.ByteString
import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.future.Polling
import com.bot4s.telegram.methods._

import scala.concurrent.Future
import scala.util.{ Failure, Success }

/**
 * This bot receives voice recordings and outputs the file size.
 */
class VoiceFileBot(token: String) extends PekkoExampleBot(token) with Polling with Commands[Future] {

  onMessage { implicit msg =>
    using(_.voice) { voice =>
      request(GetFile(voice.fileId)).andThen {
        case Success(file) =>
          file.filePath match {

            case Some(filePath) =>
              // See https://core.telegram.org/bots/api#getfile
              val url = s"https://api.telegram.org/file/bot${token}/${filePath}"

              for {
                res <- Http().singleRequest(HttpRequest(uri = Uri(url)))
                if res.status.isSuccess()
                bytes <- Unmarshal(res).to[ByteString]
                _     <- reply(s"File with ${bytes.size} bytes received.")
              } yield ()
            case None =>
              reply("No file_path was returned")
          }

        case Failure(e) =>
          logger.error("Exception: " + e) // poor's man logging
      }.void
    }
  }
}
