package info.mukel.telegram.bots.api

/**
  * Location
  *
  * This object represents a point on the map.
  * Field 	Type 	Description
  * longitude 	Float 	Longitude as defined by sender
  * latitude 	Float 	Latitude as defined by sender
  */
case class Location(
                      longitude : Double,
                      latitude  : Double
                      )
