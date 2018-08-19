package com.bot4s.telegram.models

import com.bot4s.telegram.methods.ParseMode.ParseMode

/** This object represents the content of a message to be sent as a result of an inline query.
  *
  * Telegram clients currently support the following 4 types:
  *   InputTextMessageContent
  *   InputLocationMessageContent
  *   InputVenueMessageContent
  *   InputContactMessageContent
  */
sealed trait InputMessageContent


/** Represents the content of a text message to be sent as the result of an inline query.
  *
  * @param messageText            String Text of the message to be sent, 1-4096 characters
  * @param parseMode              String Optional Send Markdown or HTML, if you want Telegram apps to show bold,
  *                               italic, fixed-width text or inline URLs in your bot's message.
  * @param disableWebPagePreview  Boolean Optional Disables link previews for links in the sent message
  */
case class InputTextMessageContent(
                                  messageText           : String,
                                  parseMode             : Option[ParseMode] = None,
                                  disableWebPagePreview : Option[Boolean] = None
                                  ) extends InputMessageContent

/** Represents the content of a location message to be sent as the result of an inline query.
  *
  * ''Note:''
  *   This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
  *
  * @param latitude   Float Latitude of the location in degrees
  * @param longitude  Float Longitude of the location in degrees
  */
case class InputLocationMessageContent(
                                      latitude  : Double,
                                      longitude : Double
                                  ) extends InputMessageContent

/** Represents the content of a venue message to be sent as the result of an inline query.
  *
  * @param latitude       Float Latitude of the venue in degrees
  * @param longitude      Float Longitude of the venue in degrees
  * @param title          String Name of the venue
  * @param address        String Address of the venue
  * @param foursquareId   String Optional Foursquare identifier of the venue, if known
  * @param foursquareType String Optional. Foursquare type of the venue, if known. (For example, “arts_entertainment/default”, “arts_entertainment/aquarium” or “food/icecream”.)
  *
  * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
  */
case class InputVenueMessageContent(
                                   latitude       : Double,
                                   longitude      : Double,
                                   title          : String,
                                   address        : String,
                                   foursquareId   : Option[String] = None,
                                   foursquareType : Option[String] = None
                                   ) extends InputMessageContent

/** Represents the content of a contact message to be sent as the result of an inline query.
  *
  * ''Note:''
  *   This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
  *
  * @param phoneNumber  String Contact's phone number
  * @param firstName    String Contact's first name
  * @param lastName     String Optional Contact's last name
  * @param vcard        String Optional. Additional data about the contact in the form of a vCard, 0-2048 bytes
  */
case class InputContactMessageContent(
                                     phoneNumber : String,
                                     firstName   : String,
                                     lastName    : Option[String] = None,
                                     vcard       : Option[String] = None
                                     ) extends InputMessageContent
