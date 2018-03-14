import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import info.mukel.telegrambot4s.akka.api.AkkaDefaults
import info.mukel.telegrambot4s.api.Polling
import info.mukel.telegrambot4s.api.declarative.Commands
import org.json4s._
import org.json4s.jackson.JsonMethods._

/**
  * CovfefeBot
  */
class CovfefeBot(token: String) extends ExampleBot(token) with AkkaDefaults with Polling with Commands {

  onCommand("/start") { implicit msg =>
    reply(
      """Make texting great again!
        |Use /covfefe to get a Trump quote.
      """.stripMargin)
  }

  onCommand("/covfefe") { implicit msg =>
    val url = "https://api.whatdoestrumpthink.com/api/v1/quotes/random"
    for {
      response <- Http().singleRequest(HttpRequest(uri = Uri(url)))
      if response.status.isSuccess()
      json <- Unmarshal(response).to[String]
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
