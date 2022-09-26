package com.bot4s.telegram.methods

import com.bot4s.telegram.models.Sticker

/**
 * Use this method to get information about custom emoji stickers by their identifiers.
 * Returns an Array of Sticker objects.
 *
 * @param customEmojiIds     Array of string. List of custom emoji identifiers. At most 200 custom emoji identifiers can be specified
 */
case class GetCustomEmojiStickers(customEmojiIds: Array[String]) extends JsonRequest[List[Sticker]]
