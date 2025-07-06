package com.bot4s.telegram.methods

import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to delete a sticker from a set created by the bot.
 * Returns True on success.
 *
 * @param sticker String	File identifier of the sticker
 */
case class DeleteStickerFromSet(sticker: String) extends JsonRequest[Boolean]

object DeleteStickerFromSet {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val deleteStickerFromSetEncoder: Encoder[DeleteStickerFromSet] = deriveConfiguredEncoder[DeleteStickerFromSet]
}
