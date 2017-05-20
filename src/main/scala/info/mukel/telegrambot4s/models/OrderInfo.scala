package info.mukel.telegrambot4s.models

/**
  * This object represents information about an order.
  *
  * @param name             String	Optional. User name
  * @param phoneNumber      String	Optional. User's phone number
  * @param email            String	Optional. User email
  * @param shippingAddress  ShippingAddress	Optional. User shipping address
  */
case class OrderInfo(
                    name            : Option[String] = None,
                    phoneNumber     : Option[String] = None,
                    email           : Option[String] = None,
                    shippingAddress : Option[ShippingAddress] = None
                    )
