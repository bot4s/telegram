package com.bot4s.telegram.methods

import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to move a sticker in a set created by the bot to a specific position.
 * Returns True on success.
 *
 * @param sticker   String File identifier of the sticker
 * @param position  Integer New sticker position in the set, zero-based
 */
case class SetStickerPositionInSet(sticker: String, position: Int) extends JsonRequest {
  type Response = Boolean
}

object SetStickerPositionInSet {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val setStickerPositionInSetEncoder: Encoder[SetStickerPositionInSet] =
    deriveConfiguredEncoder[SetStickerPositionInSet]
}
