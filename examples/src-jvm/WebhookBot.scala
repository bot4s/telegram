import java.net.URLEncoder

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.bot4s.telegram.api.Webhook
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models.Message

/**
  * Webhook-backed JS calculator.
  * To test Webhooks locally, use an SSH tunnel or ngrok.
  */
class WebhookBot(token: String) extends AkkaExampleBot(token) with Webhook {

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
        request(SendMessage(msg.source, result))
      }
    }
  }
}
