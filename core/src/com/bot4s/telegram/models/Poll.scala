package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * This object contains information about a poll.
 *
 * @param id        Unique poll identifier
 * @param question  Poll question, 1-255 characters
 * @param options   List of poll options
 * @param isClosed  True, if the poll is closed
 */
case class Poll(id: String, question: String, options: Array[PollOption], isClosed: Boolean)

object Poll {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[Poll] = deriveDecoder
  implicit val circeEncoder: Encoder[Poll] = deriveConfiguredEncoder
}
