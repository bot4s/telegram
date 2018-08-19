import com.softwaremill.sttp._
import com.bot4s.telegram.api.Polling
import com.bot4s.telegram.api.declarative.Commands

import scala.concurrent.Future

/**
  * CovfefeBot
  */
class CovfefeBot(token: String) extends ExampleBot(token) with Polling with Commands {

  onCommand("/start") { implicit msg =>
    reply("Make texting great again!\nUse /covfefe to get a Trump quote.")
  }

  onCommand("/covfefe") { implicit msg =>
    val url = "https://api.whatdoestrumpthink.com/api/v1/quotes/random"
    for {
      r <- sttp.get(uri"$url").response(asString).send[Future]()
      if r.isSuccess
      json = r.unsafeBody
    } /* do */ {
      print(json)
//      val t = io.circe.parser.parse(json).fold(throw _, identity)
//      t.hcursor.downField("message").get()
//      JField("message", JString(quote)) <- obj
//
//        reply(quote)
    }
  }
}
