package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.extras.Configuration

/**
 * This object represents a service message about a new forum topic closed in the chat. Currently holds no information
 */
case object ForumTopicClosed {
  implicit val customConfig: Configuration                  = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[ForumTopicClosed.type] = deriveDecoder
  implicit val circeEncoder: Encoder[ForumTopicClosed.type] = deriveConfiguredEncoder
}
