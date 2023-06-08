package com.bot4s.telegram.methods

/**
 * Use this method to change search keywords assigned to a regular or custom emoji sticker.
 * The sticker must belong to a sticker set that was created by the bot. Returns True on success.
 *
 * @param sticker   File identifier of the sticket
 * @param keywords  A JSON-serialized list of 0-20 search keywords for the sticker with total length of up to 64 characters
 */
case class SetStickerKeywords(
  sicket: String,
  keywords: Option[Array[String]] = None
) extends JsonRequest[Boolean]
