package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

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
  implicit val circeDecoder: Decoder[Location] = deriveDecoder[Location]
}
