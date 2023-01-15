package com.bot4s.telegram.models

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
