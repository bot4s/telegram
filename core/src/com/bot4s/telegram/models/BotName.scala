package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents the bot's name.
 *
 * @param name The bot's name
 */
case class BotName(name: String)

object BotName {
  implicit val circeDecoder: Decoder[BotName] = deriveDecoder[BotName]
}
