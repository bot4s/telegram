package com.bot4s.telegram.methods

import com.bot4s.telegram.models.StickerType.StickerType
import com.bot4s.telegram.models.StickerFormat.StickerFormat
import com.bot4s.telegram.models.InputSticker

/**
 * Use this method to create new sticker set owned by a user.
 * The bot will be able to edit the created sticker set.
 * Returns True on success.
 *
 * @param userId          Long User identifier of created sticker set owner
 * @param name            String Short name of sticker set, to be used in t.me/addstickers/ URLs (e.g., animals). Can contain only english letters, digits and underscores. Must begin with a letter, can't contain consecutive underscores and must end in “_by_<bot username>”. <bot_username> is case insensitive. 1-64 characters.
 * @param title           String Sticker set title, 1-64 characters
 * @param stickers        A JSON-serialized list of 1-50 initial stickers to be added to the sticker set
 * @param stickerFormat   Format of stickers in the set, must be one of “static”, “animated”, “video”
 * @param stickerType     StickerType. Type of stickers in the set, pass “regular” or “mask”. Custom emoji sticker sets can't be created via the Bot API at the moment. By default, a regular sticker set is created.
 * @param needsRepainting Pass True if stickers in the sticker set must be repainted to the color of text when used in messages, the accent color if used as emoji status, white on chat photos, or another appropriate color based on context; for custom emoji sticker sets only
 */
case class CreateNewStickerSet(
  userId: Long,
  name: String,
  title: String,
  stickers: Array[InputSticker],
  stickerFormat: StickerFormat,
  stickerType: Option[StickerType] = None,
  needsRepainting: Option[Boolean] = None
) extends JsonRequest[Boolean]
