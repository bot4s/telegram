package com.bot4s.telegram.methods

import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to set the thumbnail of a custom emoji sticker set. Returns True on success.
 *
 * @param name          Sticker set name
 * @param customEmojiId Custom emoji identifier of a sticker from the sticker set; pass an empty string to drop the thumbnail and use the first sticker as the thumbnail.
 */
case class SetCustomEmojiStickerSetThumbnail(
  name: String,
  customEmojiId: Option[String] = None
) extends JsonRequest[Boolean]

object SetCustomEmojiStickerSetThumbnail {
  implicit val customConfig: Configuration                              = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[SetCustomEmojiStickerSetThumbnail] = deriveConfiguredEncoder
}
