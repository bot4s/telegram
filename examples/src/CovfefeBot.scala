import cats.instances.future._
import cats.syntax.functor._
import com.softwaremill.sttp._
import com.bot4s.telegram.api.Polling
import com.bot4s.telegram.api.declarative.Commands

import scala.concurrent.Future

case class Body(message: String)

/**
  * CovfefeBot
  */
class CovfefeBot(token: String) extends ExampleBot(token)
  with Polling[Future]
  with Commands[Future] {

  onCommand("/start") { implicit msg =>
    reply("Make texting great again!\nUse /covfefe to get a Trump quote.").void
  }

  onCommand("/covfefe") { implicit msg =>
    val url = "https://api.whatdoestrumpthink.com/api/v1/quotes/random"
    for {
      r <- sttp.get(uri"$url").response(asString).send[Future]()
      if r.isSuccess
      json = r.unsafeBody
      t = io.circe.parser.parse(json).fold(throw _, identity)
      quote = t.hcursor.downField("message").as[String].right.toOption.getOrElse("")
      _ <- reply(quote)
    } yield ()
  }
}
