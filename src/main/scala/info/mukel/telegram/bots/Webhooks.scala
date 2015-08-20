package info.mukel.telegram.bots

import java.net.InetSocketAddress

import com.sun.net.httpserver._
import info.mukel.telegram.bots.api.{Update, TelegramBotApi}
import info.mukel.telegram.bots.http.ScalajHttpClient
import info.mukel.telegram.bots.json.JsonUtils

/**
 * Webhooks
 *
 * Provides updates based on web hooks 
 * The server once stopped cannot be restarted
 */
trait Webhooks extends Runnable with HttpHandler {
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

  private def respond(exchange: HttpExchange, code: Int = 200, body: String = "") {
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
      // the token must conained somewhere in the path
      if (method == "POST" && path.contains(token)) {
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
