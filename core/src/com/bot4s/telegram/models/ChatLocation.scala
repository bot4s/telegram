package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Represents a location to which a chat is connected.
 *
 * @param location   Location. The location to which the supergroup is connected. Can't be a live location.
 * @param address    String. Location address; 1-64 characters, as defined by the chat owner
 */
case class ChatLocation(
  location: Location,
  address: String
)

object ChatLocation {
  implicit val customConfig: Configuration         = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[ChatLocation] = deriveDecoder[ChatLocation]
  implicit val circeEncoder: Encoder[ChatLocation] = deriveConfiguredEncoder[ChatLocation]
}
