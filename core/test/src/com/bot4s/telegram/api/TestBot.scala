package com.bot4s.telegram.api

import cats.MonadError
import cats.instances.future.catsStdInstancesForFuture
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import com.bot4s.telegram.methods.Request
import io.circe.Decoder
import io.circe.Encoder

class TestBot extends BotBase[Future] with StrictLogging {
  override implicit val monad: MonadError[Future, Throwable] = catsStdInstancesForFuture

  override val client: RequestHandler[Future] = new RequestHandler[Future] {

    override def sendRequest[T <: Request: Encoder](request: T)(implicit
      d: Decoder[request.Response]
    ): Future[request.Response] = ???

  }

}
