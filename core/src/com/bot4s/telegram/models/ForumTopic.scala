package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * This object represents a forum topic.
 *
 * @param messageThreadId   Integer. Unique identifier of the forum topic
 * @param name              String. Name of the topic
 * @param iconColor         Integer. Color of the topic icon in RGB format
 * @param iconCustomEmojiId Optional. Unique identifier of the custom emoji shown as the topic icon
 */
case class ForumTopic(
  messageThreadId: Int,
  name: String,
  iconColor: Int,
  iconCustomEmojiId: Option[String] = None
)

object ForumTopic {
  implicit val customConfig: Configuration       = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[ForumTopic] = deriveDecoder[ForumTopic]
  implicit val circeEncoder: Encoder[ForumTopic] = deriveConfiguredEncoder[ForumTopic]
}
