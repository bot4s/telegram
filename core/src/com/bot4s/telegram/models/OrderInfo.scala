package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.{ deriveDecoder, deriveEncoder }
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * This object represents information about an order.
 *
 * @param name             String Optional. User name
 * @param phoneNumber      String Optional. User's phone number
 * @param email            String Optional. User email
 * @param shippingAddress  ShippingAddress Optional. User shipping address
 */
case class OrderInfo(
  name: Option[String] = None,
  phoneNumber: Option[String] = None,
  email: Option[String] = None,
  shippingAddress: Option[ShippingAddress] = None
)

object OrderInfo {
  implicit val customConfig: Configuration      = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[OrderInfo] = deriveDecoder[OrderInfo]
  implicit val circeEncoder: Encoder[OrderInfo] = deriveConfiguredEncoder[OrderInfo]
}
