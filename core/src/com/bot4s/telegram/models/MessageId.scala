package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents a unique message identifier.
 *
 * @param messageId Unique message identifier
 */
case class MessageId(
  messageId: Int
)

object MessageId {
  implicit val circeDecoder: Decoder[MessageId] = deriveDecoder[MessageId]
}
