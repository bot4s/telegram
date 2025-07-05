package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents the bot's description.
 *
 * @param description The bot's description
 */
case class BotDescription(description: String)

object BotDescription {
  implicit val circeDecoder: Decoder[BotDescription] = deriveDecoder[BotDescription]
}
