package com.bot4s.telegram.methods

/**
 * Use this method to delete a sticker set that was created by the bot.
 * Return True on success.
 *
 * @param name  Sticker set name
 */
case class DeleteStickerSet(
  name: String
) extends JsonRequest[Boolean]
