package com.bot4s.telegram.methods

import com.bot4s.telegram.models.InputSticker
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to add a new sticker to a set created by the bot.
 * Returns True on success.
 *
 * @param userId   User identifier of sticker set owner
 * @param name     Sticker set name
 * @param sticker  A JSON-serialized object with information about the added sticker. If exactly the same sticker had already been added to the set, then the set isn't changed.
 */
case class AddStickerToSet(
  userId: Long,
  name: String,
  sticker: InputSticker
) extends JsonRequest {
  type Response = Boolean
}

object AddStickerToSet {
  implicit val customConfig: Configuration                      = Configuration.default.withSnakeCaseMemberNames
  implicit val addStickerToSetEncoder: Encoder[AddStickerToSet] = deriveConfiguredEncoder[AddStickerToSet]
}
