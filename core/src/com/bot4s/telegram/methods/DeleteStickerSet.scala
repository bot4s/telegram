package com.bot4s.telegram.methods

import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to delete a sticker set that was created by the bot.
 * Return True on success.
 *
 * @param name  Sticker set name
 */
case class DeleteStickerSet(
  name: String
) extends JsonRequest {
  type Response = Boolean
}

object DeleteStickerSet {
  implicit val customConfig: Configuration             = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[DeleteStickerSet] = deriveConfiguredEncoder
}
