package com.bot4s.telegram.api.declarative

import com.bot4s.telegram.models.{InlineQuery, Message}

import scala.util.matching.Regex

/**
  * Regex-based commands an inline queries.
  */
trait RegexCommands[F[_]] extends Messages[F] with InlineQueries[F] {

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
  def onRegex(r: Regex)(actionWithArgs: ActionWithArgs[F, Message]): Unit = {
    onMessage { msg =>
      msg.text.map(_.trim).collect { case r(args @ _*) =>
        actionWithArgs(msg)(args)
      } getOrElse unit
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
  def onRegexInline(r: Regex)(actionWithArgs: ActionWithArgs[F, InlineQuery]): Unit = {
    onInlineQuery { iq =>
      iq.query.trim match {
        case r(args @ _*) => actionWithArgs(iq)(args)
        case _ => unit
      }
    }
  }
}
