package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.models.ResponseParameters

/**
  * Telegram API errors, e.g. when `response.ok` is false.
  * Does not wrap exceptions related to de/serialization, network errors...
  */
case class TelegramApiException(message: String, errorCode: Int, cause: Option[Throwable] = None, parameters: Option[ResponseParameters] = None)
  extends Exception(message, cause.orNull)
