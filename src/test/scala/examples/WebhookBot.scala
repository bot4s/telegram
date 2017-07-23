package examples

import java.net.URLEncoder

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.methods._
import info.mukel.telegrambot4s.models._

/**
  * Webhook-backed JS calculator.
  * To test Webhooks locally, use an SSH tunnel or ngrok.
  */
class WebhookBot(token: String) extends ExampleBot(token) with Webhook {

  val port = 8080
  val webhookUrl = "https://88c444ab.ngrok.io"

  val baseUrl = "http://api.mathjs.org/v1/?expr="

  override def receiveMessage(msg: Message): Unit = {
    for (text <- msg.text) {
      val url = baseUrl + URLEncoder.encode(text, "UTF-8")
      for {
        res <- Http().singleRequest(HttpRequest(uri = Uri(url)))
        if res.status.isSuccess()
        result <- Unmarshal(res).to[String]
      } /* do */ {
        println(result)
        request(SendMessage(msg.source, result))
      }
    }
  }
}
