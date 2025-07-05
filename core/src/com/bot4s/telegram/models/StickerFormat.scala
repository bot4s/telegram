package com.bot4s.telegram.models

import io.circe.Decoder
import com.bot4s.telegram.marshalling._

/**
 * Format of the sticker, must be one of “static”, “animated”, “video”
 */
object StickerFormat extends Enumeration {
  type StickerFormat = Value

  val Static, Animated, Video = Value

  implicit val circeDecoder: Decoder[StickerFormat] =
    Decoder[String].map(s => StickerFormat.withName(pascalize(s)))
}
