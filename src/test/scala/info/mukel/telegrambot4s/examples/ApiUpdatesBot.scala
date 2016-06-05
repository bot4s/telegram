package info.mukel.telegrambot4s.examples

import java.util.concurrent.ConcurrentHashMap

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.github.mukel.telegrambot4s._
import info.mukel.telegrambot4s.api.{Commands, Polling}
import info.mukel.telegrambot4s.Implicits
import info.mukel.telegrambot4s.methods.{ParseMode, SendMessage}

/**
  * Rustic bot to notify about Telegram Bot API changes
  */
object ApiUpdatesBot extends TestBot with Polling with Commands {

  val users = new ConcurrentHashMap[Long, Unit]
  var latestChanges: String = _

  on("/on") { implicit msg => _ =>
    users.put(msg.sender, ())
    reply("Telegram Bot API Updates notification: ON")
  }
  on("/off") { implicit msg => _ =>
    users.remove(msg.sender)
    reply("Telegram Bot API Updates notification: OFF")
  }

  def getChanges() = {
    for {
      response <- Http().singleRequest(HttpRequest(uri = Uri("https://core.telegram.org/bots/api")))
      if response.status.isSuccess()
      html <- Unmarshal(response).to[String]
    } yield {
      val t = Seq("</a>Recent changes</h3>", "<strong>").foldLeft(html)((acc, e) => acc.drop(acc.indexOf(e) + e.size))
      t.take(t.indexOf("</strong>"))
    }
  }

  on("/changes") { msg => _ =>
    for {
      changes <- getChanges()
    } /* do */ {
      api.request(SendMessage(msg.sender, changes, ParseMode.HTML))
    }
  }

  def notifyUsers(changes: String) = {
    import scala.collection.JavaConversions._
    for (u <- users.keys())
      api.request(SendMessage(u, changes))
  }

  override def run(): Unit = {
    super.run()
    while (true) {
      for {
        newChanges <- getChanges
        if (newChanges != latestChanges)
      } /* do */ {
        latestChanges = newChanges
        notifyUsers(newChanges)
      }
      Thread.sleep(120000)
    }
  }
}
