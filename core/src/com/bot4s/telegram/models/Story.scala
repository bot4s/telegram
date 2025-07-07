package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.Configuration

/**
 * This object represents a message about a forwarded story in the chat. Currently holds no information.
 */
case object Story {
  implicit val customConfig: Configuration       = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[Story.type] = deriveDecoder
  implicit val circeEncoder: Encoder[Story.type] = deriveConfiguredEncoder[Story.type]
}
