package com.bot4s.telegram.models

/** This object represents a point on the map.
  *
  * @param longitude  Longitude as defined by sender
  * @param latitude   Latitude as defined by sender
  */
case class Location(
                     longitude : Double,
                     latitude  : Double
                   )
