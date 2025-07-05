package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents a service message about an edited forum topic
 *
 * @param name                  Optional. String. The new name of the topic, if it was edited
 * @param iconCustomEmojiId     Optional. String. Unique identifier of the custom emoji shown as the topic icon
 */
case class ForumTopicEdited(
  name: String,
  iconCustomEmojiId: Option[String] = None
)

object ForumTopicEdited {
  implicit val circeDecoder: Decoder[ForumTopicEdited] = deriveDecoder[ForumTopicEdited]
}
