package info.mukel.telegrambot4s.methods

import info.mukel.telegrambot4s.models.StickerSet

/**
  * Use this method to get a sticker set.
  * On success, a StickerSet object is returned.
  *
  * @param name  String Name of the sticker set
  */
case class GetStickerSet(name: String) extends ApiRequestJson[StickerSet]
