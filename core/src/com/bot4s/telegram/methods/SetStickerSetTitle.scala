package com.bot4s.telegram.methods

import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to set the title of a created sticker set. Returns True on success.
 *
 * @param name  Sticker set name
 * @param title Sticket set title, 1-64 characters
 */
case class SetStickerSetTitle(
  name: String,
  title: String
) extends JsonRequest {
  type Response = Boolean
}

object SetStickerSetTitle {
  implicit val customConfig: Configuration               = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[SetStickerSetTitle] = deriveConfiguredEncoder
}
