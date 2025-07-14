package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * This object represents the bot's description.
 *
 * @param description The bot's description
 */
case class BotDescription(description: String)

object BotDescription {
  implicit val customConfig: Configuration           = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[BotDescription] = deriveDecoder
  implicit val circeEncoder: Encoder[BotDescription] = deriveConfiguredEncoder
}
