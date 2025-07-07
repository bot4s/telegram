package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Contains data sent from a Web App to the bot.
 *
 * @param data String. Be aware that a bad client can send arbitrary data in this field.
 * @param buttonText String. Text of the web_app keyboard button, from which the Web App was opened. Be aware that a bad client can send arbitrary data in this field.
 */
case class WebAppData(
  data: String,
  buttonText: String
)

object WebAppData {
  implicit val customConfig: Configuration       = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[WebAppData] = deriveDecoder[WebAppData]
  implicit val circeEncoder: Encoder[WebAppData] = deriveConfiguredEncoder
}
