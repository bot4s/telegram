package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

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
  implicit val circeDecoder: Decoder[ShippingQuery] = deriveDecoder[ShippingQuery]
}
