package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents a service message about General forum topic unhidden in the chat. Currently holds no information.
 */
case object GeneralForumTopicUnhidden {
  implicit val circeDecoder: Decoder[GeneralForumTopicUnhidden.type] = deriveDecoder
}
