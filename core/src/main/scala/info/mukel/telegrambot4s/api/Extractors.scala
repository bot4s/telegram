package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.api.declarative._
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
    * Extract clean command, lowercased and without receiver.
    */
  def command(msg: Message): Option[Command] =
    rawCommand(msg).map(ToCommand.cleanCommand)

  /**
    * Extract trimmed command without '/'. Receiver is included, if exists.
    */
  def rawCommand(msg: Message): Option[Command] =
    textTokens(msg)
      .map(_.head)
      .filter(_.startsWith(ToCommand.CommandPrefix))
      .map(_.trim.stripPrefix(ToCommand.CommandPrefix))

  /**
    * Tokenize message text; drops first token (/command).
    */
  def commandArguments(msg: Message): Option[Args] = textTokens(msg).map(_.tail)
}
