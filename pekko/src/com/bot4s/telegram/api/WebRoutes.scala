package com.bot4s.telegram.api

import org.apache.pekko.http.scaladsl.{ Http, HttpsConnectionContext }
import org.apache.pekko.http.scaladsl.server.Directives._
import org.apache.pekko.http.scaladsl.server.Route
import com.bot4s.telegram.future.BotExecutionContext
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.{ Future, Promise }

trait WebRoutes extends BotBase[Future] with StrictLogging {
  this: BotExecutionContext with PekkoImplicit =>

  val port: Int
  val interfaceIp: String                          = "::0"
  val httpsContext: Option[HttpsConnectionContext] = None

  def routes: Route = reject

  private var bindingFuture: Future[Http.ServerBinding] = _

  @volatile private var eol: Promise[Unit] = _

  abstract override def run(): Future[Unit] = synchronized {
    if (eol != null) {
      throw new RuntimeException("Bot is already running")
    }

    bindingFuture = (httpsContext match {
      case Some(httpsCtx) => Http().newServerAt(interfaceIp, port).enableHttps(httpsCtx)
      case None           => Http().newServerAt(interfaceIp, port)
    }).bindFlow(routes)

    bindingFuture.foreach { _ =>
      logger.info(s"Listening on $interfaceIp:$port")
    }

    sys.addShutdownHook {
      shutdown()
    }

    eol = Promise[Unit]()
    val t = Future.sequence(Seq(eol.future, super.run()))
    t.map(_ => ())
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
