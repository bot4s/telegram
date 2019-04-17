package com.bot4s.telegram.clients

import cats.instances.future._
import com.softwaremill.sttp.SttpBackend

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class FutureSttpClient(token: String, telegramHost: String = "api.telegram.org")
  (implicit backend: SttpBackend[Future, Nothing], ec: ExecutionContext)
  extends SttpClient[Future](token, telegramHost)
