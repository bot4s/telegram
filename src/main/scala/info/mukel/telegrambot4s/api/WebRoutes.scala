package info.mukel.telegrambot4s.api

import akka.http.scaladsl.Http

import akka.http.scaladsl.server.{Directives, Route}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

trait WebRoutes extends BotBase with AkkaImplicits with Directives {

  val port: Int
  val interfaceIp: String = "::0"

  def routes: Route = reject

  private var bindingFuture: Future[Http.ServerBinding] = _

  override def run(): Unit = {
    bindingFuture = Http().bindAndHandle(routes, interfaceIp, port)
    bindingFuture.foreach { _ =>
      logger.info(s"Listening on $interfaceIp:$port")
    }

    sys.addShutdownHook {
      Await.ready(shutdown(), 30.seconds)
    }
  }

  def shutdown(): Future[Unit] = {
    for {
      b <- bindingFuture
      _ <- b.unbind()
      t <- system.terminate()
    } yield ()
  }
}

