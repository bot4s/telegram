package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.models.{InlineQuery, Message}

import scala.util.matching.Regex

/**
  * Regex-based commands an inline queries.
  */
trait RegexCommands extends Messages with InlineQueries {

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
  def onRegex(r: Regex)(actionWithArgs: ActionWithArgs[Message]): Unit = {
    onMessage { msg =>
      msg.text.map(_.trim).collect { case r(args @ _*) => actionWithArgs(msg)(args) }
    }
  }

  /**
    * Filter inline queries messages using a regular expression.
    * Captured groups are passed along with the message to the handler.
    * The query's is trimmed before applying the regex, no need to take care of leading/trailing spaces.
    *
    * '''Warning:'''
    *   Absent optional groups won't be ignored, `null` will be passed instead.
    */
  def onRegexInline(r: Regex)(actionWithArgs: ActionWithArgs[InlineQuery]): Unit = {
    onInlineQuery { iq =>
      iq.query.trim match {
        case r(args @ _*) => actionWithArgs(iq)(args)
        case _ =>
      }
    }
  }
}
