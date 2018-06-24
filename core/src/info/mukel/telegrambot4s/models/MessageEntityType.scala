package info.mukel.telegrambot4s.models

/**
  * Different types of in-message entities.
  */
object MessageEntityType extends Enumeration {
  type MessageEntityType = Value

  val
  Bold,
  BotCommand,
  Cashtag,
  Code,
  Email,
  Hashtag,
  Italic,
  Mention,
  PhoneNumber,
  Pre,
  TextLink,
  TextMention,
  Unknown,
  Url = Value
}
