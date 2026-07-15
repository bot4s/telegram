package com.bot4s.telegram.clients

import cats.instances.future._

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import sttp.client4.Backend

class FutureSttpClient(token: String, telegramHost: String = "api.telegram.org")(implicit
  backend: Backend[Future],
  ec: ExecutionContext
) extends SttpClient[Future](token, telegramHost)
