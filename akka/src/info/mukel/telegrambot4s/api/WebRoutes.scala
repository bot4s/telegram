package info.mukel.telegrambot4s.api

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.typesafe.scalalogging.Logger

import scala.concurrent.{Future, Promise}

trait WebRoutes extends BotBase {
  _: BotExecutionContext with AkkaImplicits =>

  private val logger = Logger("WebRoutes")

  val port: Int
  val interfaceIp: String = "::0"

  def routes: Route = reject

  private var bindingFuture: Future[Http.ServerBinding] = _

  @volatile private var eol: Promise[Unit] = _

  abstract override def run(): Future[Unit] = synchronized {
    if (eol != null) {
      throw new RuntimeException("Bot is already running")
    }
    super.run()
    bindingFuture = Http().bindAndHandle(routes, interfaceIp, port)
    bindingFuture.foreach { _ =>
      logger.info(s"Listening on $interfaceIp:$port")
    }

    sys.addShutdownHook {
      shutdown()
    }

    eol = Promise[Unit]()
    eol.future
  }

  abstract override def shutdown(): Unit = synchronized {
    if (eol == null) {
      throw new RuntimeException("Bot is not running")
    }
    super.shutdown()
    for {
       b <- bindingFuture
       _ <- b.unbind()
       t <- system.terminate()
    } /* do */ {
      eol.success(())
      eol = null
    }
  }
}
