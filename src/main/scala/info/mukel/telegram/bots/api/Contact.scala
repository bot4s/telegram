package info.mukel.telegram.bots.api

/**
 * Contact
 *
 * This object represents a phone contact.
 *
 * @param phoneNumber  Contact's phone number
 * @param firstName    Contact's first name
 * @param lastName     Optional. Contact's last name
 * @param userId       Optional. Contact's user identifier in Telegram
 */
case class Contact(
                     phoneNumber : String,
                     firstName   : String,
                     lastName    : Option[String] = None,
                     userId      : Option[Int] = None
                     )
