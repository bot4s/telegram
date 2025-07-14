package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.{ deriveDecoder, deriveEncoder }
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * This object contains information about an incoming shipping query.
 *
 * @param id               String Unique query identifier
 * @param from             User User who sent the query
 * @param invoicePayload   String Bot specified invoice payload
 * @param shippingAddress  ShippingAddress User specified shipping address
 */
case class ShippingQuery(
  id: String,
  from: User,
  invoicePayload: String,
  shippingAddress: ShippingAddress
)

object ShippingQuery {
  implicit val customConfig: Configuration          = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[ShippingQuery] = deriveDecoder
  implicit val circeEncoder: Encoder[ShippingQuery] = deriveConfiguredEncoder
}
