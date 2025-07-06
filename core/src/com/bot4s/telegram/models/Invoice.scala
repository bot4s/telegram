package com.bot4s.telegram.models

import com.bot4s.telegram.models.Currency.Currency
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * This object contains basic information about an invoice.
 *
 * @param title          String Product name
 * @param description    String Product description
 * @param startParameter String Unique bot deep-linking parameter that can be used to generate this invoice
 * @param currency       String Three-letter ISO 4217 currency code
 * @param totalAmount    Integer Total price in the smallest units of the currency (integer, not float/double). For example, for a price of US$ 1.45 pass amount = 145. See the exp parameter in currencies.json, it shows the number of digits past the decimal point for each currency (2 for the majority of currencies).
 */
case class Invoice(
  title: String,
  description: String,
  startParameter: String,
  currency: Currency,
  totalAmount: Long
)

object Invoice {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[Invoice] = deriveDecoder[Invoice]
  implicit val circeEncoder: Encoder[Invoice] = deriveConfiguredEncoder[Invoice]
}
