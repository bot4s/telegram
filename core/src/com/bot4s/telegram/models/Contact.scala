package com.bot4s.telegram.models

/** This object represents a phone contact.
  *
  * @param phoneNumber  Contact's phone number
  * @param firstName    Contact's first name
  * @param lastName     Optional Contact's last name
  * @param userId       Optional Contact's user identifier in Telegram
  * @param vcard        String Optional. Additional data about the contact in the form of a vCard
  */
case class Contact(
                    phoneNumber : String,
                    firstName   : String,
                    lastName    : Option[String] = None,
                    userId      : Option[Int] = None,
                    vcard       : Option[String] = None
                  )
