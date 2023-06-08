package com.bot4s.telegram.methods

/**
 * Use this method to set the title of a created sticker set. Returns True on success.
 *
 * @param name  Sticker set name
 * @param title Sticket set title, 1-64 characters
 */
case class SetStickerSetTitle(
  name: String,
  title: String
) extends JsonRequest[Boolean]
