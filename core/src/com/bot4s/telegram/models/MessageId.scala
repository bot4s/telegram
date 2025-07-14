package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.Configuration

/**
 * This object represents a unique message identifier.
 *
 * @param messageId Unique message identifier
 */
case class MessageId(
  messageId: Int
)

object MessageId {
  implicit val customConfig: Configuration      = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[MessageId] = deriveDecoder[MessageId]
  implicit val circeEncoder: Encoder[MessageId] = deriveConfiguredEncoder[MessageId]

}
