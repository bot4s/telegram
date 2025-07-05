package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * A placeholder, currently holds no information. Use BotFather to set up your game.
 */
sealed trait CallbackGame

object CallbackGame extends CallbackGame {
  implicit val circeDecoder: Decoder[CallbackGame] = Decoder.const(CallbackGame)
}
