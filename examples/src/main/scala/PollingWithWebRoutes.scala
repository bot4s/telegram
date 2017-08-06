

import info.mukel.telegrambot4s.api.declarative.Commands
import info.mukel.telegrambot4s.api.{Polling, WebRoutes}

/**
  * Showcases the ability to run Polling and WebRoutes at the same time.
  * Check http://localhost:8080 on your local browser.
  * It provides a simple way to add custom endpoints for authentication, games
  * and even serving entire websites.
  */
class PollingWithWebRoutes(token: String) extends ExampleBot(token)
  with Polling
  with WebRoutes
  with Commands {

  override val port: Int = 8080

  onCommand("/hello") { implicit msg =>
    reply("Hello")
  }

  import akka.http.scaladsl.server.Directives._

  override def routes = pathEndOrSingleSlash {
    complete("I'm running...")
  } ~ super.routes
}
