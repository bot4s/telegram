package info.mukel.telegram.bots.v2.examples

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import akka.util.ByteString
import info.mukel.telegram.bots.v2.api.Implicits._
import info.mukel.telegram.bots.v2.methods.{SendMessage, SendPhoto}
import info.mukel.telegram.bots.v2.model._
import info.mukel.telegram.bots.v2.{Commands, Polling}

import scala.concurrent.ExecutionContext.Implicits._

/**
  * Created by mukel on 5/9/16.
  */
object DogeBot extends TestBot with Polling with Commands {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  val http = Http(system)

  on("doge") { implicit message => args =>
    val url = "http://dogr.io/" + (args mkString "/") + ".png?split=false"
    for {
      response <- http.singleRequest(HttpRequest(uri = Uri(url)))
      bytes <-  Unmarshal(response).to[ByteString]
    } /* do */ {
      val photo = InputFile.FromByteString("doge.png", bytes)
      api.request(SendPhoto(message.sender, photo))
    }
  }

}
