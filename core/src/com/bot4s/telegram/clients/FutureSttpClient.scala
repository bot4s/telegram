package com.bot4s.telegram.clients

import cats.instances.future._

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import sttp.client3.SttpBackend

class FutureSttpClient(token: String, telegramHost: String = "api.telegram.org")
  (implicit backend: SttpBackend[Future, Any], ec: ExecutionContext)
  extends SttpClient[Future](token, telegramHost)
