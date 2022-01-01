package com.bot4s.telegram.models

/**
 * Different types of in-message entities.
 */
object MessageEntityType extends Enumeration {
  type MessageEntityType = Value

  val Bold, BotCommand, Cashtag, Code, Email, Spoiler, Hashtag, Italic, Mention, PhoneNumber, Pre, TextLink,
    TextMention, Unknown, Url = Value
}
