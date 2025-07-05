package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents a message about a forwarded story in the chat. Currently holds no information.
 */
case object Story {
  implicit val circeDecoder: Decoder[Story.type] = deriveDecoder
}
