package com.bot4s.telegram.cats

import cats.MonadError
import com.bot4s.telegram.api.BotBase
import com.bot4s.telegram.clients.SttpClient
import com.softwaremill.sttp.SttpBackend

class TelegramBot[F[_]] (
  token: String,
  backend: SttpBackend[F, Nothing],
  telegramHost: String = "api.telegram.org"
)(implicit monadError: MonadError[F, Throwable]) extends BotBase[F] {

  override val monad = monadError

  implicit private val b = backend
  val client = new SttpClient[F](token, telegramHost)
}
