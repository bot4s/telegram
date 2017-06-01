package info.mukel.telegrambot4s.examples

import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.models.Message

import scala.util.matching.Regex

class RegexBot(token: String) extends ExampleBot(token) with Polling with RegexCommands {
  onRegex("""^\s*/regex\s*(\w+)\s*$""".r) { implicit msg => groups =>
    reply(groups mkString ", ")
  }

  onRegex("""^1?$|^(11+?)\1+$""".r) { implicit msg => groups =>
    reply("Not prime!")
  }
}

trait RegexCommands extends BotBase with Actions {
  type MessageActionWithArgs = Message => Seq[String] => Unit
  def onRegex(r: Regex)(actionWithArgs: MessageActionWithArgs): Unit = {
    foreachMessage { msg =>
      msg.text.collect { case r(args @ _*) => actionWithArgs(msg)(args) }
    }
  }
}
