package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents the bot's short description.
 *
 * @param shortDescription The bot's short description
 */
case class BotShortDescription(shortDescription: String)

object BotShortDescription {
  implicit val circeDecoder: Decoder[BotShortDescription] = deriveDecoder[BotShortDescription]
}
