package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents a service message about a video chat started in the chat. Currently holds no information.
 */
case object VideoChatStarted {
  implicit val circeDecoder: Decoder[VideoChatStarted.type] = deriveDecoder
}
