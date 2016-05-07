package info.mukel.telegram.bots.v2

import akka.NotUsed
import akka.stream.SourceShape
import akka.stream.scaladsl.{Broadcast, Concat, Flow, GraphDSL, Source, ZipWith}
import info.mukel.telegram.bots.v2.methods.{GetUpdates, SendMessage}
import info.mukel.telegram.bots.v2.model.Update
import info.mukel.telegram.bots.v2.api.Implicits._
import info.mukel.telegram.bots.v2.api.TelegramApiAkka

import scala.concurrent.Future

/**
  * Created by mukel on 5/7/16.
  */
object EchoBot extends TelegramBot with Polling {
  override val api =  new TelegramApiAkka("105458118:AAEq33cj4vIk8-anDQFwzBh4WV8rbFCO4U0")
  override def handleUpdate(update: Update): Unit = {
    update.message.foreach {
      msg =>
        api.request(SendMessage(msg.chat.id, msg.text.getOrElse("EMPTY")))
    }
  }
}

object Main extends App {
  EchoBot.run()
}
