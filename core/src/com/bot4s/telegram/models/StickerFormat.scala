package com.bot4s.telegram.models

/**
 * Format of the sticker, must be one of “static”, “animated”, “video”
 */

object StickerFormat extends Enumeration {
  type StickerFormat = Value

  val Static, Animated, Video = Value
}
