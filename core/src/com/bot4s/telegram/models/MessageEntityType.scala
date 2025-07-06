package com.bot4s.telegram.models

import io.circe.{Decoder, Encoder}
import com.bot4s.telegram.marshalling._

/**
 * Different types of in-message entities.
 */
object MessageEntityType extends Enumeration {
  type MessageEntityType = Value

  val Bold, BotCommand, Cashtag, Code, Email, Spoiler, Hashtag, Italic, Mention, PhoneNumber, Pre, TextLink,
    TextMention, Unknown, Url, CustomEmoji = Value

  implicit val circeDecoder: Decoder[MessageEntityType] =
    Decoder[String].map { s =>
      try {
        MessageEntityType.withName(pascalize(s))
      } catch {
        case e: NoSuchElementException =>
          MessageEntityType.Unknown
      }
    }
  implicit val circeEncoder: Encoder[MessageEntityType] =
    Encoder[String].contramap[MessageEntityType](e => CaseConversions.snakenize(e.toString))
}
