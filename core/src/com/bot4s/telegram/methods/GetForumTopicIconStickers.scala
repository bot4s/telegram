package com.bot4s.telegram.methods

import com.bot4s.telegram.models.Sticker

/**
 * Use this method to get custom emoji stickers, which can be used as a forum topic icon by any user.
 * Returns an Array of Sticker objects.
 */
case object GetForumTopicIconStickers extends JsonRequest[List[Sticker]]
