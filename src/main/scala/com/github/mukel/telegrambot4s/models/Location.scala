package com.github.mukel.telegrambot4s.models

/** Location
  *
  * This object represents a point on the map.
  *
  * @param longitude  Longitude as defined by sender
  * @param latitude   Latitude as defined by sender
  */
case class Location(
                     longitude : Double,
                     latitude  : Double
                   )
