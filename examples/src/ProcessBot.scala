import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.api.Polling
import com.bot4s.telegram.api.declarative.Commands

import scala.concurrent.Future
import scala.util.control.NonFatal

/**
  * Runs commands and reply with the output.
  */
class ProcessBot(token: String) extends ExampleBot(token)
  with Polling[Future]
  with Commands[Future] {

  onCommand('run | 'exec | 'execute | 'cmd) { implicit msg =>
    withArgs {
      args =>
        try {
          import sys.process._
          val result = args.mkString(" ") !!

          reply(result).void
        } catch {
          case NonFatal(e) =>
            reply("Exception: " + e.getMessage).void
        }
    }
  }
}
