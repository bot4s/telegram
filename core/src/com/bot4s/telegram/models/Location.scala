package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.Configuration

/**
 * This object represents a point on the map.
 *
 * @param longitude  Longitude as defined by sender
 * @param latitude   Latitude as defined by sender
 */
case class Location(
  longitude: Double,
  latitude: Double
)

object Location {
  implicit val customConfig: Configuration     = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[Location] = deriveDecoder[Location]
  implicit val circeEncoder: Encoder[Location] = deriveConfiguredEncoder[Location]
}
