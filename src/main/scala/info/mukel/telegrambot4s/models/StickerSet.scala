package info.mukel.telegrambot4s.models

/**
  * This object represents a sticker set.
  *
  * @param name           String Sticker set name
  * @param title          String Sticker set title
  * @param containsMasks  Boolean True, if the sticker set contains masks
  * @param stickers       Array of Sticker List of all set stickers
  */
case class StickerSet(
                       name          : String,
                       title         : String,
                       containsMasks : Boolean,
                       stickers      : Array[Sticker])