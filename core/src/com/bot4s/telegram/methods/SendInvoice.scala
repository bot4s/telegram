package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{InlineKeyboardMarkup, LabeledPrice, Message}
import com.bot4s.telegram.models.Currency.Currency
import com.bot4s.telegram.models.Message

/**
  * Use this method to send invoices.
  * On success, the sent Message is returned.
  *
  * @param chatId               Integer Yes Unique identifier for the target private chat
  * @param title                String Yes Product name
  * @param description          String Yes Product description
  * @param payload              String Yes Bot-defined invoice payload, 1-128 bytes. This will not be displayed to the user, use for your internal processes.
  * @param providerToken        String Yes Payments provider token, obtained via Botfather
  * @param startParameter       String Yes Unique deep-linking parameter that can be used to generate this invoice when used as a start parameter
  * @param currency             String Yes Three-letter ISO 4217 currency code, see more on currencies
  * @param prices               Array of LabeledPrice Yes Price breakdown, a list of components (e.g. product price, tax, discount, delivery cost, delivery tax, bonus, etc.)
  * @param providerData         String Optional JSON-encoded data about the invoice, which will be shared with the payment provider.
  *                             A detailed description of required fields should be provided by the payment provider.
  * @param photoUrl             String Optional URL of the product photo for the invoice.
  *                             Can be a photo of the goods or a marketing image for a service.
  *                             People like it better when they see what they are paying for.
  * @param photoSize            Integer Optional Photo size
  * @param photoWidth           Integer Optional Photo width
  * @param photoHeight          Integer Optional Photo height
  * @param needName             Bool Optional Pass True, if you require the user's full name to complete the order
  * @param needPhoneNumber      Boolean Optional Pass True, if you require the user's phone number to complete the order
  * @param needEmail            Bool Optional Pass True, if you require the user's email to complete the order
  * @param needShippingAddress  Boolean Optional Pass True, if you require the user's shipping address to complete the order
  * @param isFlexible           Boolean Optional Pass True, if the final price depends on the shipping method
  * @param disableNotification  Boolean Optional Sends the message silently. Users will receive a notification with no sound.
  * @param replyToMessageId     Integer Optional If the message is a reply, ID of the original message
  * @param replyMarkup          InlineKeyboardMarkup Optional A JSON-serialized object for an inline keyboard.
  *                             If empty, one 'Pay total price' button will be shown.
  *                             If not empty, the first button must be a Pay button.
  */
case class SendInvoice(chatId: Long,
                       title: String,
                       description: String,
                       payload: String,
                       providerToken: String,
                       startParameter: String,
                       currency: Currency,
                       prices: Array[LabeledPrice],
                       providerData: Option[String] = None,
                       photoUrl: Option[String] = None,
                       photoSize: Option[Int] = None,
                       photoWidth: Option[Int] = None,
                       photoHeight: Option[Int] = None,
                       needName: Option[Boolean] = None,
                       needPhoneNumber: Option[Boolean] = None,
                       needEmail: Option[Boolean] = None,
                       needShippingAddress: Option[Boolean] = None,
                       isFlexible: Option[Boolean] = None,
                       disableNotification: Option[Int] = None,
                       replyToMessageId: Option[Long] = None,
                       replyMarkup: Option[InlineKeyboardMarkup] = None)
    extends JsonRequest[Message]
