package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents a service message about a video chat ended in the chat.
 *
 * @param duration
 */
case class VideoChatEnded(
  duration: Int
)

object VideoChatEnded {
  implicit val circeDecoder: Decoder[VideoChatEnded] = deriveDecoder[VideoChatEnded]
}
