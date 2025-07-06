package com.bot4s.telegram.models

import io.circe.{Decoder, Encoder, JsonObject}
import io.circe.generic.semiauto.deriveDecoder

/**
 * A placeholder, currently holds no information. Use BotFather to set up your game.
 */
sealed trait CallbackGame

object CallbackGame extends CallbackGame {
  implicit val circeDecoder: Decoder[CallbackGame] = Decoder.const(CallbackGame)
  implicit val circeEncoder: Encoder[CallbackGame] =
    Encoder.encodeJsonObject.contramap[CallbackGame](_ => JsonObject.empty)
}
