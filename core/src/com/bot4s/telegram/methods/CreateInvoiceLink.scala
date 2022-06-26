package com.bot4s.telegram.methods

import com.bot4s.telegram.models.LabeledPrice

/**
 * Use this method to create an additional invite link for a chat.
 * The bot must be an administrator in the chat for this to work and must have the appropriate administrator rights.
 * The link can be revoked using the method revokeChatInviteLink. Returns the new invite link as ChatInviteLink object.
 *
 * @param title                     String. Product name, 1-32 characters
 * @param description               String. Product description, 1-255 characters
 * @param payload                   String. Bot-defined invoice payload, 1-128 bytes. This will not be displayed to the user, use for your internal processes.
 * @param providerToken             String. Payment provider token, obtained via BotFather
 * @param currency                  String. Three-letter ISO 4217 currency code, see more on currencies
 * @param prices                    Array of LabeledPrice. Price breakdown, a JSON-serialized list of components (e.g. product price, tax, discount, delivery cost, delivery tax, bonus, etc.)
 * @param maxTipAmount              Integer Optional. The maximum accepted amount for tips in the smallest units of the currency (integer, not float/double). For example, for a maximum tip of US$ 1.45 pass maxTipAmount = 145.
 *                                    See the exp parameter in currencies.json, it shows the number of digits past the decimal point for each currency (2 for the majority of currencies). Defaults to 0
 * @param suggestedTipAmounts       Array of Integer Optional. A JSON-serialized array of suggested amounts of tips in the smallest units of the currency (integer, not float/double).
 *                                    At most 4 suggested tip amounts can be specified. The suggested tip amounts must be positive, passed in a strictly increased order and must not exceed maxTipAmount.
 * @param providerData              String Optional.  JSON-serialized data about the invoice, which will be shared with the payment provider. A detailed description of required fields should be provided by the payment provider.
 * @param photoUrl                  String Optional. URL of the product photo for the invoice. Can be a photo of the goods or a marketing image for a service.
 * @param photoSize                 Integer Optional. Photo size in bytes
 * @param photoWidth                Integer Optional. Photo width
 * @param photoHeight               Integer Optional. Photo height
 * @param needName                  Boolean Optional. Pass True, if you require the user's full name to complete the order
 * @param needPhoneNumber           Boolean Optional. Pass True, if you require the user's phone number to complete the order
 * @param needEmail                 Boolean Optional. Pass True, if you require the user's email address to complete the order
 * @param needShippingAddress       Boolean Optional. Pass True, if you require the user's shipping address to complete the order
 * @param sendPhoneNumberToProvider Boolean Optional. Pass True, if the user's phone number should be sent to the provider
 * @param sendEmailToProvider       Boolean Optional. Pass True, if the user's email address should be sent to the provider
 * @param isFlexible                Boolean Optional. Pass True, if the final price depends on the shipping method
 */
case class CreateInvoiceLink(
  title: String,
  description: String,
  payload: String,
  providerToken: String,
  currency: String,
  prices: List[LabeledPrice],
  maxTipAmount: Option[Int] = None,
  suggestedTipAmounts: Option[Array[Int]] = None,
  providerData: Option[String] = None,
  photoUrl: Option[String] = None,
  photoSize: Option[Int] = None,
  photoWidth: Option[Int] = None,
  photoHeight: Option[Int] = None,
  needName: Option[Boolean] = None,
  needPhoneNumber: Option[Boolean] = None,
  needEmail: Option[Boolean] = None,
  needShippingAddress: Option[Boolean] = None,
  sendPhoneNumberToProvider: Option[Boolean] = None,
  sendEmailToProvider: Option[Boolean] = None,
  isFlexible: Option[Boolean] = None
) extends JsonRequest[String] {

  require(title.length > 0 && title.length <= 32, "Title must be less than 32 characters")
  require(description.length > 0 && description.length <= 255, "Description must be less than 255 characters")
  require(payload.getBytes.length > 0 && payload.getBytes.length <= 128, "Payload must be less than 128 bytes")
}
