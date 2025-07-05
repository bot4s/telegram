package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents a service message about General forum topic hidden in the chat. Currently holds no information.
 */
case object GeneralForumTopicHidden {
  implicit val circeDecoder: Decoder[GeneralForumTopicHidden.type] = deriveDecoder
}
