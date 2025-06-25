package com.bot4s.telegram.api

import cats.MonadError
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.Future

class TestBot extends BotBase[Future] with StrictLogging {
  override lazy val client: RequestHandler[Future] = ???

  override implicit val monad: MonadError[Future, Throwable] = MonadError[Future, Throwable]
}
