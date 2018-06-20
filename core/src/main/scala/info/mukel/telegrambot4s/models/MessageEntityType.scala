package info.mukel.telegrambot4s.models

/**
  * Different types of in-message entities.
  */
object MessageEntityType extends Enumeration {
  type MessageEntityType = Value

  val PhoneNumber = Value("phone_number")
  val Cashtag = Value("cashtag")
  val Message = Value("message")
  val EditedMessage = Value("edited_message")
  val Mention = Value("mention")
  val Hashtag = Value("hashtag")
  val BotCommand = Value("bot_command")
  val Url = Value("url")
  val Email = Value("email")
  val Bold = Value("bold")
  val Italic = Value("italic")
  val Code = Value("code")
  val Pre = Value("pre")
  val TextLink = Value("text_link")
  val TextMention = Value("text_mention")
}
