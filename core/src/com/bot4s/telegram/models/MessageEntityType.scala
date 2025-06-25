package com.bot4s.telegram.models

/**
 * Different types of in-message entities.
 */
enum MessageEntityType:
  case Bold, BotCommand, Cashtag, Code, Email, Spoiler, Hashtag, Italic, Mention, PhoneNumber, Pre, TextLink, TextMention, Unknown, Url, CustomEmoji
