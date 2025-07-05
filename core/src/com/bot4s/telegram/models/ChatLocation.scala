package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

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
  implicit val circeDecoder: Decoder[ChatLocation] = deriveDecoder[ChatLocation]
}
