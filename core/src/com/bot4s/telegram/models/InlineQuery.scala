package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.{ deriveDecoder, deriveEncoder }
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * This object represents an incoming inline query.
 *
 * When the user sends an empty query, your bot could return some default or trending results.
 *
 * @param id        String Unique identifier for this query
 * @param from      User Sender
 * @param location  Location Optional Sender location, only for bots that request user location
 * @param query     String Text of the query
 * @param offset    String Offset of the results to be returned, can be controlled by the bot
 */
case class InlineQuery(
  id: String,
  from: User,
  location: Option[Location] = None,
  query: String,
  offset: String
)

object InlineQuery {
  implicit val customConfig: Configuration        = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[InlineQuery] = deriveDecoder
  implicit val circeEncoder: Encoder[InlineQuery] = deriveConfiguredEncoder
}
