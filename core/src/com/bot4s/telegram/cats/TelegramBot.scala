package com.bot4s.telegram.cats

import cats.MonadError
import com.bot4s.telegram.api.BotBase
import com.bot4s.telegram.clients.SttpClient
import sttp.client3.SttpBackend

class TelegramBot[F[_]](
  token: String,
  backend: SttpBackend[F, Any],
  telegramHost: String = "api.telegram.org"
)(implicit monadError: MonadError[F, Throwable])
    extends BotBase[F] {

  override val monad = monadError

  implicit private val b: SttpBackend[F, Any] = backend
  val client                                  = new SttpClient[F](token, telegramHost)
}
