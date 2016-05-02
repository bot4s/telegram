package info.mukel.telegram.bots.v2

import info.mukel.telegram.bots.v2.model._
import info.mukel.telegram.bots.v2.api._
import scala.util.{Failure, Success}

case object GetMe extends ApiRequest[User]

object Test extends App {

  val api = new TelegramApi("YOU_TOKEN_HERE")

  api.request(GetMe).onComplete {
    case Success(user) => println(user)
    case Failure(e) => println(e)
  }
}
