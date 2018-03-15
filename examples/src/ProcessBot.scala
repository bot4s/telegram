import info.mukel.telegrambot4s.api.Polling
import info.mukel.telegrambot4s.api.declarative.Commands

import scala.util.control.NonFatal

/**
  * Runs commands and reply with the output.
  */
class ProcessBot(token: String) extends ExampleBot(token) with Polling with Commands {
  onCommand('run | 'exec | 'execute | 'cmd) { implicit msg =>
    withArgs {
      args =>
        try {
          import sys.process._
          val result = args.mkString(" ") !!

          reply(result)
        } catch {
          case NonFatal(e) =>
            reply("Exception: " + e.getMessage)
        }
    }
  }
}
