package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.extras.Configuration

/**
 * This object represents a service message about a video chat ended in the chat.
 *
 * @param duration
 */
case class VideoChatEnded(
  duration: Int
)

object VideoChatEnded {
  implicit val customConfig: Configuration           = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[VideoChatEnded] = deriveDecoder[VideoChatEnded]
  implicit val circeEncoder: Encoder[VideoChatEnded] = deriveConfiguredEncoder
}
