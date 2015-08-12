package info.mukel.telegram.bots

import java.net.InetSocketAddress

import com.sun.net.httpserver._
import info.mukel.telegram.bots.api.{Update, TelegramBotApi}
import info.mukel.telegram.bots.http.ScalajHttpClient
import info.mukel.telegram.bots.json.JsonUtils

trait Webhooks extends HttpHandler {
  this : TelegramBot =>

  val webHookUrl: String
  val hostname: String = "localhost"
  val port: Int = 1234
  val backlog: Int = 0

  private val address = new InetSocketAddress(hostname, port)
  private val server = HttpServer.create(address, backlog)

  import OptionPimps._

  override def run(): Unit = {
    server.createContext("/", this)
    server.start
    setWebhook(webHookUrl)
  }

  def respond(exchange: HttpExchange, code: Int = 200, body: String = "") {
    val bytes = body.getBytes
    exchange.sendResponseHeaders(code, bytes.size)
    val out = exchange.getResponseBody
    out.write(bytes)
    out.write("\r\n\r\n".getBytes)
    out.close()
    exchange.close()
  }

  override def handle(exchange: HttpExchange) = {
    val method = exchange.getRequestMethod
    val path = exchange.getRequestURI.getPath

    println(method + " " + path)
    try {
      if (method == "POST" && path.stripPrefix("/") == token) {
        val body = scala.io.Source.fromInputStream(exchange.getRequestBody).mkString
        handleUpdate(JsonUtils.unjsonify[Update](body))
        respond(exchange, 200, "OK!")
      } else
        // NOT FOUND
        respond(exchange, 404)
    } catch {
        case ex: Exception => respond(exchange, 500, ex.toString)
    }
  }

  def stop(delay: Int = 1) = {
    setWebhook(None)
    server.stop(delay)
  }
}

object WebhookedBot extends TelegramBot(Utils.tokenFromFile("./flunkeybot.token")) with Webhooks with Commands {
  override val webHookUrl: String = "https://webhooks.mukel.info/" + token
  on("hello") { (sender, _) =>
    replyTo(sender) {
      "Hello World! " + System.currentTimeMillis()
    }
  }
}

object Test {
  def main(args: Array[String]): Unit = {
    (new TelegramBotApi(Utils.tokenFromFile("./flunkeybot.token")) with ScalajHttpClient).setWebhook(None)
    //WebhookedBot.run()
  }
}
