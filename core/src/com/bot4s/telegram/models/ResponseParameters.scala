package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * Contains information about why a request was unsuccessful.
 *
 * @param migrateToChatId  Integer Optional. The group has been migrated to a supergroup with the specified identifier.
 *                         This number may be greater than 32 bits and some programming languages may have
 *                         difficulty/silent defects in interpreting it.
 *                         But it is smaller than 52 bits, so a signed 64 bit integer or double-precision float type are safe for storing this identifier.
 * @param retryAfter       Integer Optional. In case of exceeding flood control, the number of seconds left to wait before the request can be repeated
 */
case class ResponseParameters(
  migrateToChatId: Option[Long] = None,
  retryAfter: Option[Int] = None
)

object ResponseParameters {
  implicit val circeDecoder: Decoder[ResponseParameters] = deriveDecoder[ResponseParameters]
}
