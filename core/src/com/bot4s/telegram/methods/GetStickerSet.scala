package com.bot4s.telegram.methods

import com.bot4s.telegram.models.StickerSet
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to get a sticker set.
 * On success, a StickerSet object is returned.
 *
 * @param name  String Name of the sticker set
 */
case class GetStickerSet(name: String) extends JsonRequest[StickerSet]

object GetStickerSet {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val getStickerSetEncoder: Encoder[GetStickerSet] = deriveConfiguredEncoder[GetStickerSet]
}
