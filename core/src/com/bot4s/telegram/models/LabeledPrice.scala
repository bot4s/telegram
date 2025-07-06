package com.bot4s.telegram.models

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * This object represents a portion of the price for goods or services.
 *
 * @param label String Portion label
 * @param amount Integer Price of the product in the smallest units of the currency (integer, not float/double).
 *               For example, for a price of US$ 1.45 pass amount = 145.
 *               See the exp parameter in currencies.json, it shows the number of digits past the decimal point for each currency (2 for the majority of currencies).
 */
case class LabeledPrice(
  label: String,
  amount: Long
)

object LabeledPrice {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[LabeledPrice] = deriveDecoder[LabeledPrice]
  implicit val circeEncoder: Encoder[LabeledPrice] = deriveConfiguredEncoder[LabeledPrice]
}
