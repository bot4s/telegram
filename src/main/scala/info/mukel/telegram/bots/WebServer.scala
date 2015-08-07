package info.mukel.telegram.bots

import java.net.InetSocketAddress

import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer}

import scala.collection.mutable._

abstract class SimpleHttpServerBase(val socketAddress: String = "127.0.0.1",
                                    val port: Int = 88,
                                    val backlog: Int = 0) extends HttpHandler {

  private val address = new InetSocketAddress(socketAddress, port)
  private val server = HttpServer.create(address, backlog)
  server.createContext("/", this)

  def redirect(url: String) = {
    s"""<html>
      <head>
        <meta http-equiv="Refresh" content=${"0," + url}/>
      </head>
      <body>
        You are being redirected to:
        <a href=${url}>
          ${url}
        </a>
      </body>
    </html>"""
  }

  def respond(exchange: HttpExchange, code: Int = 200, body: String = "") {
    val bytes = body.getBytes
    exchange.sendResponseHeaders(code, bytes.size)
    exchange.getResponseBody.write(bytes)
    exchange.getResponseBody.write("\r\n\r\n".getBytes)
    exchange.getResponseBody.close()
    exchange.close()
  }

  def start() = server.start()

  def stop(delay: Int = 1) = server.stop(delay)
}

abstract class SimpleHttpServer extends SimpleHttpServerBase {
  private val mappings = HashMap[String, Map[String, () => Any]]() withDefaultValue HashMap.empty
  mappings.put("GET", HashMap.empty)
  mappings.put("POST", HashMap.empty)

  def on(method: String)(path: String)(action: => Any) = mappings(method) += path -> (() => action)

  def GET = on("GET") _
  def POST = on("POST") _

  def handle(exchange: HttpExchange) = {

    val method = exchange.getRequestMethod

    mappings(method).get(exchange.getRequestURI.getPath) match {
      case None => respond(exchange, 404)
      case Some(action) => try {
        val t = action
        respond(exchange, 200, action().toString)
      } catch {
        case ex: Exception => respond(exchange, 500, ex.toString)
      }
    }
  }

}

class HelloApp extends SimpleHttpServer {
  GET("/") {
    "There's nothing here"
  }

  GET("/hello") {
    "Hello, world!"
  }

  GET("/error") {
    throw new RuntimeException("Bad bad error occurred")
  }
}
/*
object Main {

  def main(args: Array[String]) {
    val server = new HelloApp()
    server.start()
  }
}*/
