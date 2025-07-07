package com.bot4s.telegram.methods

import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Once the user has confirmed their payment and shipping details,
 * the Bot API sends the final confirmation in the form of an Update with the field pre_checkout_query.
 * Use this method to respond to such pre-checkout queries.
 * On success, True is returned.
 * '''Note:'''
 *   The Bot API must receive an answer within 10 seconds after the pre-checkout query was sent.
 *
 * @param preCheckoutQueryId  String Yes Unique identifier for the query to be answered
 * @param ok                  Boolean Yes Specify True if everything is alright (goods are available, etc.)
 *                            and the bot is ready to proceed with the order.
 *                            Use False if there are any problems.
 * @param errorMessage        String Optional Required if ok is False.
 *                            Error message in human readable form that explains the reason for failure to proceed with the checkout
 *                            (e.g.
 *                              "Sorry, somebody just bought the last of our amazing black T-shirts while you were busy filling out your payment details.
 *                               Please choose a different color or garment!").
 *                            Telegram will display this message to the user.
 */
case class AnswerPreCheckoutQuery(preCheckoutQueryId: String, ok: Boolean, errorMessage: Option[String] = None)
    extends JsonRequest {
  type Response = Boolean

  require(ok || errorMessage.isDefined, "errorMessage is required if ok is False")
}

object AnswerPreCheckoutQuery {
  implicit val customConfig: Configuration                   = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[AnswerPreCheckoutQuery] = deriveConfiguredEncoder
}
