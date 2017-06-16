package info.mukel.telegrambot4s.api

import scala.util.matching.Regex

/**
  * Execute actions based on a regex.
  */
trait RegexCommands extends Actions {

  /**
    * Uses a regular expression to filter messages.
    * Captured groups are passed along with the message to the handler.
    */
  def onRegex(r: Regex)(actionWithArgs: MessageActionWithArgs): Unit = {
    foreachMessage { msg =>
      msg.text.map(_.trim).collect { case r(args @ _*) => actionWithArgs(msg)(args) }
    }
  }
}
