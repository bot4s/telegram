package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.api.declarative.Args
import info.mukel.telegrambot4s.models.Message

import scala.util.Try

object Extractors {

  object Int { def unapply(s: String) = Try(s.toInt).toOption }
  object Long { def unapply(s: String) = Try(s.toLong).toOption }
  object Double { def unapply(s: String) = Try(s.toDouble).toOption }
  object Float { def unapply(s: String) = Try(s.toFloat).toOption }

  /**
    * Tokenize message text.
    */
  def textTokens(msg: Message): Option[Args] = msg.text.map(_.trim.split("\\s+"))

  /**
    * Tokenize message text; drops first token (/command).
    */
  def commandArguments(msg: Message): Option[Args] = textTokens(msg).map(_.tail)
}
