package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.{ deriveDecoder, deriveEncoder }
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * This object represents one shipping option.
 *
 * @param id     String Shipping option identifier
 * @param title  String Option title
 * @param prices Array of LabeledPrice List of price portions
 */
case class ShippingOption(
  id: String,
  title: String,
  prices: Array[LabeledPrice]
)

object ShippingOption {
  implicit val customConfig: Configuration           = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[ShippingOption] = deriveDecoder[ShippingOption]
  implicit val circeEncoder: Encoder[ShippingOption] = deriveConfiguredEncoder[ShippingOption]
}
