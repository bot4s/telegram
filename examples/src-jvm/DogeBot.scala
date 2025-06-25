import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.api.ChatActions
import com.bot4s.telegram.future.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models.InputFile

import scala.concurrent.Future
import sttp.client3._
import sttp.client3.okhttp.OkHttpFutureBackend

/**
 * Such Telegram, many bots, so Dogesome.
 */
class DogeBot(token: String) extends ExampleBot(token) with Polling with Commands[Future] with ChatActions[Future] {

  onCommand("/doge") { implicit msg =>
    withArgs { args =>
      val url = "http://dogr.io/" + (args mkString "/") + ".png?split=false"
      for {
        res <- backend.send(basicRequest.get(uri"$url").response(asByteArray))
        if res.code.isSuccess
        bytes = res.body match {
          case Right(b) => b
          case Left(e) => throw new RuntimeException(s"Failed to get image: $e")
        }
        _     = println(bytes.length)
        photo = InputFile("doge.png", bytes)
        _    <- uploadingPhoto // Hint the user
        _    <- request(SendPhoto(msg.source, photo))
      } yield ()
    }
  }
}
