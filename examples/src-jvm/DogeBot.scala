import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.api.{Polling, _}
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models.InputFile

import scala.concurrent.Future

/**
  * Such Telegram, many bots, so Dogesome.
  */
class DogeBot(token: String) extends ExampleBot(token)
  with Polling
  with Commands
  with ChatActions {

  onCommand("/doge") { implicit msg =>
    withArgs { args =>
      val url = "http://dogr.io/" + (args mkString "/") + ".png?split=false"
      for {
        res <- Future { scalaj.http.Http(url).asBytes }
        if res.isSuccess
        bytes = res.body
      } /* do */ {
        println(bytes.length)
        val photo = InputFile("doge.png", bytes)
        uploadingPhoto // Hint the user
        request(SendPhoto(msg.source, photo))
      }
    }
  }
}
