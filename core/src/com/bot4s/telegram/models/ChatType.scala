package com.bot4s.telegram.models

import io.circe.Decoder
import com.bot4s.telegram.marshalling._

/**
 * Type of chat, can be either "private", "group", "supergroup" or "channel"
 */
object ChatType extends Enumeration {
  type ChatType = Value
  val Private, Group, Supergroup, Channel = Value

  implicit val circeDecoder: Decoder[ChatType] =
    Decoder[String].map(s => ChatType.withName(pascalize(s)))
}
