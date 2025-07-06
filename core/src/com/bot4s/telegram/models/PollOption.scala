package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * This object contains information about one answer option in a poll.
 *
 * @param text        Option text, 1-100 characters
 * @param voterCount  Number of users that voted for this option
 */
case class PollOption(text: String, voterCount: Int)

object PollOption {
  implicit val customConfig: Configuration       = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[PollOption] = deriveDecoder
  implicit val circeEncoder: Encoder[PollOption] = deriveConfiguredEncoder
}
