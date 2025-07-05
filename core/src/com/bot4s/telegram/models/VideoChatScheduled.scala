package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents a service message about a video chat scheduled in the chat.
 *
 * @param startDate  Point in time (Unix timestamp) when the video chat is supposed to be started by a chat administrator
 */
case class VideoChatScheduled(
  startDate: Int
)

object VideoChatScheduled {
  implicit val circeDecoder: Decoder[VideoChatScheduled] = deriveDecoder[VideoChatScheduled]
}
