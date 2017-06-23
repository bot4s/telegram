package info.mukel.telegrambot4s.examples

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import info.mukel.telegrambot4s.api.Polling
import info.mukel.telegrambot4s.api.declarative.BetterCommands
import org.json4s._
import org.json4s.jackson.JsonMethods._

/**
  * CovfefeBot
  */
class CovfefeBot(token: String) extends ExampleBot(token) with Polling with BetterCommands {

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
