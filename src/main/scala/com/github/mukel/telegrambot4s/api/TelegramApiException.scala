package com.github.mukel.telegrambot4s.api

/** Wraps Telegram API errors. (When response.ok = false)
  * Not intended to wrap exceptions related to de/serialization, network...
  */
case class TelegramApiException(message: String, errorCode: Int, cause: Option[Throwable] = None)
  extends RuntimeException(message, cause.orNull) { }
