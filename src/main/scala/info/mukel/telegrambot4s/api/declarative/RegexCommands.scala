package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.api.MessageActionWithArgs

import scala.util.matching.Regex

/**
  * Execute actions based on a regex.
  */
trait RegexCommands extends Messages {

  /**
    * Filter messages using a regular expression.
    * Captured groups are passed along with the message to the handler.
    * The message's text is trimmed before applying the regex, no need to take care of leading/trailing spaces.
    *
    * '''Warning:'''
    *   Absent optional groups won't be ignored, `null` will be passed instead.
    *
    * {{{
    *   onRegex("""/vote([0-9])""".r) {
    *     implicit msg => args =>
    *       reply("Voted: " + args)
    *   }
    * }}}
    */
  def onRegex(r: Regex)(actionWithArgs: MessageActionWithArgs): Unit = {
    onMessage { msg =>
      msg.text.map(_.trim).collect { case r(args @ _*) => actionWithArgs(msg)(args) }
    }
  }
}
