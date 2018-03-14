package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.models.ResponseParameters

/** Wraps Telegram API errors. (When response.ok = false)
  * Not intended to wrap exceptions related to de/serialization, network...
  */
case class TelegramApiException(message: String, errorCode: Int, cause: Option[Throwable] = None, parameters: Option[ResponseParameters] = None)
  extends Exception(message, cause.orNull)
