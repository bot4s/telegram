import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.api.{Polling, _}

import scala.util.Failure

/**
  * Shows exception handling
  */
class ExceptionBot(token: String) extends ExampleBot(token)
  with Polling
  with Commands {

  onCommand("/hello") { implicit msg =>
    reply("Hey there") onComplete {
      case Failure(tex: TelegramApiException) =>
        tex.errorCode match {
          case 439 => // Too many requests
            Thread.sleep(1000)
            reply("Houston, we have a (congestion) problem!")
          case 404 => println("Not found")
          case 401 => println("Unauthorized")
          case 400 => println("Bad Request")
          case e => println(s"Error code: $e")
        }
      case _ =>
    }
  }
}

