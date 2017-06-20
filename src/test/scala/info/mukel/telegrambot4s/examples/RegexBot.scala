package info.mukel.telegrambot4s.examples

import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.api.declarative.Messages

class RegexBot(token: String) extends ExampleBot(token) with Polling with Messages {

  onRegex("""/regex\s+(\w+)""".r) { implicit msg => groups =>
    reply(groups mkString ", ")
  }

  onRegex("""1?|^(11+?)\1+""".r) { implicit msg => _ =>
    reply("Not prime!")
  }
}
