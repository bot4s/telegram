import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.api.ChatActions
import com.bot4s.telegram.future.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models.InputFile

import scala.concurrent.Future

/**
 * Such Telegram, many bots, so Dogesome.
 */
class DogeBot(token: String) extends ExampleBot(token) with Polling with Commands[Future] with ChatActions[Future] {

  onCommand("/doge") { implicit msg =>
    withArgs { args =>
      val url = "http://dogr.io/" + (args mkString "/") + ".png?split=false"
      for {
        res <- Future(scalaj.http.Http(url).asBytes)
        if res.isSuccess
        bytes = res.body
        _     = println(bytes.length)
        photo = InputFile("doge.png", bytes)
        _ <- uploadingPhoto // Hint the user
        _ <- request(SendPhoto(msg.source, photo))
      } yield ()
    }
  }
}
