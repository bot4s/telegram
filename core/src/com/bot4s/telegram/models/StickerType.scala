package com.bot4s.telegram.models

/**
 * Different types of sticker
 */
object StickerType extends Enumeration {
  type StickerType = Value

  val Regular, Mask, CustomEmoji, Unknown = Value
}
