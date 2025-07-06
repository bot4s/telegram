package com.bot4s.telegram.models

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.Configuration

/**
 * This object represents a phone contact.
 *
 * @param phoneNumber  Contact's phone number
 * @param firstName    Contact's first name
 * @param lastName     Optional Contact's last name
 * @param userId       Optional Contact's user identifier in Telegram
 * @param vcard        String Optional. Additional data about the contact in the form of a vCard
 */
case class Contact(
  phoneNumber: String,
  firstName: String,
  lastName: Option[String] = None,
  userId: Option[Long] = None,
  vcard: Option[String] = None
)

object Contact {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[Contact] = deriveDecoder[Contact]
  implicit val circeEncoder: Encoder[Contact] = deriveConfiguredEncoder[Contact]
}
