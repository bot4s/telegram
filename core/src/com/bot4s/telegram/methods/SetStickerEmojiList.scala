package com.bot4s.telegram.methods

/**
 * Use this method to change the list of emoji assigned to a regular or custom emoji sticker
 * The sticker must belong to a sticker set that was created by the bot. Returns True on success.
 *
 * @param sticker   File identifier of the sticket
 * @param emojiList A JSON-serialized list of 1-20 emoji associated with the sticker
 */
case class SetStickerEmojiList(
  sicket: String,
  emojiList: Option[Array[String]] = None
) extends JsonRequest[Boolean]
