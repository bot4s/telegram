package info.mukel.telegrambot4s.models

import info.mukel.telegrambot4s.models.Country.Country

/**
  * This object represents a shipping address.
  *
  * Country codes can be easily found/validated using the following Map:
  *   Locale.getISOCountries().map(cc => (cc, new Locale("", cc).getDisplayCountry())).toMap
  *
  * @param countryCode  String	ISO 3166-1 alpha-2 country code
  * @param state        String	State, if applicable
  * @param city         String	City
  * @param streetLine1  String	First line for the address
  * @param streetLine2  String	Second line for the address
  * @param postCode     String	Address post code
  */
case class ShippingAddress(
                          countryCode : Country,
                          state       : String,
                          city        : String,
                          streetLine1 : String,
                          streetLine2 : String,
                          postCode    : String
                          )