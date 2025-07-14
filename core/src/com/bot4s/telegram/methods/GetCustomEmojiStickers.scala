package com.bot4s.telegram.methods

import com.bot4s.telegram.models.Sticker
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to get information about custom emoji stickers by their identifiers.
 * Returns an Array of Sticker objects.
 *
 * @param customEmojiIds     Array of string. List of custom emoji identifiers. At most 200 custom emoji identifiers can be specified
 */
case class GetCustomEmojiStickers(customEmojiIds: Array[String]) extends JsonRequest {
  type Response = List[Sticker]
}

object GetCustomEmojiStickers {
  implicit val customConfig: Configuration                   = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[GetCustomEmojiStickers] = deriveConfiguredEncoder
}
