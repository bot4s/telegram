
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.ByteString
import info.mukel.telegrambot4s.akka.api.AkkaDefaults
import info.mukel.telegrambot4s.api.Polling
import info.mukel.telegrambot4s.api.declarative.Commands
import info.mukel.telegrambot4s.methods._

import scala.util.{Failure, Success}

/**
  * This bot receives voice recordings and outputs the file size.
  */
class VoiceFileBot(token: String) extends AkkaExampleBot(token)
  with Polling
  with Commands {

  onMessage { implicit msg =>

    using(_.voice) { voice =>
      request(GetFile(voice.fileId)).onComplete {
        case Success(file) =>
          file.filePath match {

            case Some(filePath) =>
              // See https://core.telegram.org/bots/api#getfile
              val url = s"https://api.telegram.org/file/bot${token}/${filePath}"

              for {
                res <- Http().singleRequest(HttpRequest(uri = Uri(url)))
                if res.status.isSuccess()
                bytes <- Unmarshal(res).to[ByteString]
              } /* do */ {
                reply(s"File with ${bytes.size} bytes received.")
              }

            case None =>
              println("No file_path was returned")
          }

        case Failure(e) =>
          println("Exception: " + e) // poor's man logging
      }
    }
  }
}
