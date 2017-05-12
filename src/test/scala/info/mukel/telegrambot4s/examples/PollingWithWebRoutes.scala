package info.mukel.telegrambot4s.examples

import info.mukel.telegrambot4s.api.{Commands, Polling, WebRoutes, Webhook}

/**
  * Created by mukel on 5/12/17.
  */
class PollingWithWebRoutes(token: String) extends ExampleBot(token) with Polling with WebRoutes with Commands {
  override val port: Int = 8080

  import akka.http.scaladsl.server.Directives._

  on("/hello", "Say hello") {
    implicit  msg => _ =>
      reply("Hello")
  }

  override def routes = pathEndOrSingleSlash {
    complete("La tecnica es la tecnica")
  } ~ super.routes
}
