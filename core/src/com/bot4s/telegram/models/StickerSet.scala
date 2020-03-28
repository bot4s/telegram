package com.bot4s.telegram.models

/**
  * This object represents a sticker set.
  *
  * @param name           String Sticker set name
  * @param title          String Sticker set title
  * @param isAnimated     Boolean True, if the sticker set contains animated stickers
  * @param containsMasks  Boolean True, if the sticker set contains masks
  * @param stickers       Array of Sticker List of all set stickers
  */
case class StickerSet(name: String,
                      title: String,
                      isAnimated: Boolean,
                      containsMasks: Boolean,
                      stickers: Array[Sticker])
