package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents a service message about a new forum topic closed in the chat. Currently holds no information
 */
case object ForumTopicClosed {
  implicit val circeDecoder: Decoder[ForumTopicClosed.type] = deriveDecoder
}
