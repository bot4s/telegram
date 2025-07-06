package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * This object represents the bot's short description.
 *
 * @param shortDescription The bot's short description
 */
case class BotShortDescription(shortDescription: String)

object BotShortDescription {
  implicit val customConfig: Configuration                = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[BotShortDescription] = deriveDecoder[BotShortDescription]
  implicit val circeEncoder: Encoder[BotShortDescription] = deriveConfiguredEncoder
}
