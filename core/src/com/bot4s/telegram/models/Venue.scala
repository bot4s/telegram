package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.extras.Configuration

/**
 * This object represents a venue.
 *
 * @param location       Location Venue location
 * @param title          String Name of the venue
 * @param address        String Address of the venue
 * @param foursquareId   String Optional Foursquare identifier of the venue
 * @param foursquareType String Optional. Foursquare type of the venue. (For example, "arts_entertainment/default", "arts_entertainment/aquarium" or "food/icecream".)
 */
case class Venue(
  location: Location,
  title: String,
  address: String,
  foursquareId: Option[String] = None,
  foursquareType: Option[String] = None
)

object Venue {
  implicit val customConfig: Configuration  = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[Venue] = deriveDecoder
  implicit val circeEncoder: Encoder[Venue] = deriveConfiguredEncoder
}
