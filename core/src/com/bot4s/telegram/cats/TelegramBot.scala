package com.bot4s.telegram.cats

import cats.MonadError
import com.bot4s.telegram.api.BotBase
import com.bot4s.telegram.clients.SttpClient
import com.bot4s.telegram.log.Logger
import sttp.client.SttpBackend

class TelegramBot[F[_]: Logger](
  token: String,
  backend: SttpBackend[F, Nothing, Any],
  telegramHost: String = "api.telegram.org"
)(implicit monadError: MonadError[F, Throwable])
    extends BotBase[F] {

  val monad: MonadError[F, Throwable] = monadError

  implicit private val b = backend
  val client = new SttpClient[F](token, telegramHost, Logger.apply)
}
