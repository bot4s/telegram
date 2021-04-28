package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ShippingOption

/**
 * If you sent an invoice requesting a shipping address and the parameter is_flexible was specified,
 * the Bot API will send an Update with a shipping_query field to the bot.
 * Use this method to reply to shipping queries.
 * On success, True is returned.
 *
 * @param shippingQueryId  String Yes Unique identifier for the query to be answered
 * @param ok               Boolean Yes Specify True if delivery to the specified address is possible
 *                         and False if there are any problems (for example, if delivery to the specified address is not possible)
 * @param shippingOptions  Array of ShippingOption Optional Required if ok is True.
 *                         A JSON-serialized array of available shipping options.
 * @param errorMessage     String Optional Required if ok is False.
 *                         Error message in human readable form that explains why it is impossible to complete the order
 *                         (e.g. "Sorry, delivery to your desired address is unavailable').
 *                         Telegram will display this message to the user.
 */
case class AnswerShippingQuery(
  shippingQueryId: String,
  ok: Boolean,
  shippingOptions: Option[Array[ShippingOption]] = None,
  errorMessage: Option[String] = None
) extends JsonRequest[Boolean] {

  require(!ok || shippingOptions.isDefined, "shippingOptions required if ok is True")
  require(ok || errorMessage.isDefined, "errorMessage required if ok is False")
}
