package com.bot4s.telegram.methods

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
