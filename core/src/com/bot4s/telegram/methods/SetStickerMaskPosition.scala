package com.bot4s.telegram.methods

import com.bot4s.telegram.models.MaskPosition

/**
 * Use this method to change the mask position of a mask sticker.
 * The sticker must belong to a sticker set that was created by the bot. Returns True on success.
 *
 * @param sticker      File identifier of the sticket
 * @param maskPosition A JSON-serialized object with the position where the mask should be placed on faces. Omit the parameter to remove the mask position.
 */
case class SetStickerMaskPosition(
  sticker: String,
  maskPosition: Option[MaskPosition] = None
) extends JsonRequest[Boolean]
