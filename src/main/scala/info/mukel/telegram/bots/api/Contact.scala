package info.mukel.telegram.bots.api

/**
  * Contact
  *
  * This object represents a phone contact.
  * Field 	Type 	Description
  * phone_number 	String 	Contact's phone number
  * first_name 	String 	Contact's first name
  * last_name 	String 	Optional. Contact's last name
  * user_id 	Integer 	Optional. Contact's user identifier in Telegram
  */
case class Contact(
                     phoneNumber : String,
                     firstName   : String,
                     lastName    : Option[String] = None,
                     userId      : Option[Int] = None
                     )
