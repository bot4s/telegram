package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.extras.Configuration

/**
 * This object represents a service message about a video chat started in the chat. Currently holds no information.
 */
case object VideoChatStarted {
  implicit val customConfig: Configuration                  = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[VideoChatStarted.type] = deriveDecoder
  implicit val circeEncoder: Encoder[VideoChatStarted.type] = deriveConfiguredEncoder
}
