package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.methods.GetMe
import slogging.StrictLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TestBot extends BotBase with StrictLogging {
  override lazy val client: RequestHandler = ???

  import info.mukel.telegrambot4s.marshalling.CirceMarshaller._

  override def run(): Future[Unit] = {
    for {
      getMe_ <- request(GetMe)
    } yield {
      getMe = getMe_
      ()
    }
  }
}
