package info.mukel.telegrambot4s.examples

import java.util.concurrent.ConcurrentHashMap

import com.github.mukel.telegrambot4s._
import info.mukel.telegrambot4s.Commands
import info.mukel.telegrambot4s.api.{Commands, Polling}

object AntonBot extends TestBot with Polling with Commands {
  val activeUsers = new ConcurrentHashMap[Long, Unit]
  on("/on") { implicit msg => _ =>
    activeUsers.put(msg.sender, ())
    reply("I'm active")
  }
  on("/off") { implicit msg => _ =>
    activeUsers.remove(msg.sender)
  }
  on("/echo") { implicit msg => args =>
    if (activeUsers.contains(msg.sender))
      for (text <- msg.text)
        reply(args mkString " ")
  }
}
