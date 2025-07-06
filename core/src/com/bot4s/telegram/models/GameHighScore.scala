package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.Configuration

/**
 * This object represents one row of the high scores table for a game.
 *
 * @param position  Integer Position in high score table for the game
 * @param user      User User
 * @param score     Integer Score
 */
case class GameHighScore(
  position: Long,
  user: User,
  score: Long
)

object GameHighScore {
  implicit val customConfig: Configuration          = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[GameHighScore] = deriveDecoder[GameHighScore]
  implicit val circeEncoder: Encoder[GameHighScore] = deriveConfiguredEncoder[GameHighScore]
}
