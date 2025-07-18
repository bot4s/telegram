package com.bot4s.telegram.models

import com.bot4s.telegram.models.Currency.Currency
import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.{ deriveDecoder, deriveEncoder }
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * This object contains basic information about a successful payment.
 *
 * @param currency                 String Three-letter ISO 4217 currency code
 * @param totalAmount              Integer Total price in the smallest units of the currency (integer, not float/double).
 *                                 For example, for a price of US$ 1.45 pass amount = 145.
 *                                 See the exp parameter in currencies.json, it shows the number of digits past the decimal point for each currency (2 for the majority of currencies).
 * @param invoicePayload           String Bot specified invoice payload
 * @param shippingOptionId         String Optional. Identifier of the shipping option chosen by the user
 * @param orderInfo                OrderInfo Optional. Order info provided by the user
 * @param telegramPaymentChargeId  String Telegram payment identifier
 * @param providerPaymentChargeId  String Provider payment identifier
 */
case class SuccessfulPayment(
  currency: Currency,
  totalAmount: Long,
  invoicePayload: String,
  shippingOptionId: Option[String] = None,
  orderInfo: Option[OrderInfo] = None,
  telegramPaymentChargeId: String,
  providerPaymentChargeId: String
)

object SuccessfulPayment {
  implicit val customConfig: Configuration              = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[SuccessfulPayment] = deriveDecoder[SuccessfulPayment]
  implicit val circeEncoder: Encoder[SuccessfulPayment] = deriveConfiguredEncoder[SuccessfulPayment]
}
