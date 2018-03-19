import info.mukel.telegrambot4s.api.Polling
import info.mukel.telegrambot4s.api.declarative.Commands
import org.json4s._
import org.json4s.jackson.JsonMethods._

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
      r <- Future {
        scalaj.http.Http(url).asString
      }
      if r.isSuccess
      json = r.body
    } /* do */ {
      for {
        JObject(obj) <- parse(json)
        JField("message", JString(quote)) <- obj
      } /* do */ {
        reply(quote)
      }
    }
  }
}
