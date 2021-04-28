package com.bot4s.telegram.methods

import com.bot4s.telegram.models.StickerSet

/**
 * Use this method to get a sticker set.
 * On success, a StickerSet object is returned.
 *
 * @param name  String Name of the sticker set
 */
case class GetStickerSet(name: String) extends JsonRequest[StickerSet]
