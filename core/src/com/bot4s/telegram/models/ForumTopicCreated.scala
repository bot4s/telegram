package com.bot4s.telegram.models

/**
 * This object represents a service message about a new forum topic created in the chat
 *
 * @param name                  String. The name of the topic
 * @param iconColor             Integer. Color of the topic icon in RGB format
 * @param iconCustomEmojiId     Optional. String. Unique identifier of the custom emoji shown as the topic icon
 */
case class ForumTopicCreated(
  name: String,
  iconColor: Int,
  iconCustomEmojiId: Option[Int] = None
)
