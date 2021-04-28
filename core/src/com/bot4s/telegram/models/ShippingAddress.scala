package com.bot4s.telegram.models

import com.bot4s.telegram.models.CountryCode.CountryCode

/**
 * This object represents a shipping address.
 * See [[CountryCode]] for a full listing.
 *
 * Country codes can be easily found/validated using the following:
 * {{{
 *   Locale
 *     .getISOCountries()
 *     .map(cc => (cc, new Locale("", cc)
 *     .getDisplayCountry()))
 *     .toMap
 * }}}
 *
 * @param countryCode  String ISO 3166-1 alpha-2 country code
 * @param state        String State, if applicable
 * @param city         String City
 * @param streetLine1  String First line for the address
 * @param streetLine2  String Second line for the address
 * @param postCode     String Address post code
 */
case class ShippingAddress(
  countryCode: CountryCode,
  state: String,
  city: String,
  streetLine1: String,
  streetLine2: String,
  postCode: String
)
