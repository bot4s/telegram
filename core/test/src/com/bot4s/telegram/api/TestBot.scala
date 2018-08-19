package com.bot4s.telegram.api

import com.bot4s.telegram.methods.GetMe
import slogging.StrictLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TestBot extends BotBase with StrictLogging {
  override lazy val client: RequestHandler = ???

  override def run(): Future[Unit] = {
    for {
      getMe_ <- request(GetMe)
    } yield {
      getMe = getMe_
      ()
    }
  }
}
