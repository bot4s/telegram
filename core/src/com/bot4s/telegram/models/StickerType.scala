package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import com.bot4s.telegram.marshalling._

/**
 * Different types of sticker
 */
object StickerType extends Enumeration {
  type StickerType = Value

  val Regular, Mask, CustomEmoji, Unknown = Value

  implicit val circeDecoder: Decoder[StickerType] =
    Decoder[String].map { s =>
      try {
        StickerType.withName(pascalize(s))
      } catch {
        case e: NoSuchElementException =>
          StickerType.Unknown
      }
    }
  implicit val circeEncoder: Encoder[StickerType] =
    Encoder[String].contramap[StickerType](e => snakenize(e.toString))
}
