package com.bot4s.telegram.cats

import cats.MonadError
import cats.effect.Async
import com.bot4s.telegram.api.BotBase
import com.bot4s.telegram.clients.SttpClient
import com.softwaremill.sttp.SttpBackend
import com.softwaremill.sttp.SttpBackendOptions
import com.softwaremill.sttp.asynchttpclient.cats.AsyncHttpClientCatsBackend

class TelegramBot[F[_]: Async] private (
  token: String,
  telegramHost: String,
  backend: SttpBackend[F, Nothing]
) extends BotBase[F] {

  override val monad = Async[F]

  def this(
    token: String,
    telegramHost: String,
    options: SttpBackendOptions
  ) = this(token, telegramHost, AsyncHttpClientCatsBackend(options))

  def this(token: String, telegramHost: String) =
    this(token, telegramHost, AsyncHttpClientCatsBackend())

  def this(token: String) = this(token, "api.telegram.org")

  implicit private val b = backend
  val client = new SttpClient[F](token, telegramHost)
}
