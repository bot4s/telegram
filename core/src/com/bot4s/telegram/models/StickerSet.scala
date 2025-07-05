package com.bot4s.telegram.models

import com.bot4s.telegram.models.StickerType.StickerType
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents a sticker set.
 *
 * @param name           String Sticker set name
 * @param title          String Sticker set title
 * @param stickerType    Type of stickers in the set
 * @param isAnimated     Boolean True, if the sticker set contains animated stickers
 * @param isVideo        Boolean True, if the sticker set contains video stickers
 * @param containsMasks  Boolean True, if the sticker set contains masks, deprecated, use stickerType instead
 * @param stickers       Array of Sticker List of all set stickers
 */
case class StickerSet(
  name: String,
  title: String,
  stickerType: StickerType,
  isAnimated: Boolean,
  isVideo: Boolean,
  containsMasks: Boolean,
  stickers: Array[Sticker]
)

object StickerSet {
  implicit val circeDecoder: Decoder[StickerSet] = deriveDecoder[StickerSet]
}
