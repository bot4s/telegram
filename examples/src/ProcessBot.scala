import info.mukel.telegrambot4s.api.{Polling, _}
import info.mukel.telegrambot4s.api.declarative.Commands

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
          case e: Exception =>
            reply("Exception: " + e.getMessage)
        }
    }
  }
}

