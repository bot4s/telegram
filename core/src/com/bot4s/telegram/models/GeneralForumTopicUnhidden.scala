package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * This object represents a service message about General forum topic unhidden in the chat. Currently holds no information.
 */
case object GeneralForumTopicUnhidden {
  implicit val customConfig: Configuration                           = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[GeneralForumTopicUnhidden.type] = deriveDecoder
  implicit val circeEncoder: Encoder[GeneralForumTopicUnhidden.type] = deriveConfiguredEncoder
}
