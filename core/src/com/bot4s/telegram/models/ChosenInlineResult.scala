package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.{ deriveDecoder, deriveEncoder }
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * This object represents a result of an inline query that was chosen by the user and sent to their chat partner.
 *
 * @param resultId         String The unique identifier for the result that was chosen.
 * @param from             User The user that chose the result.
 * @param location         Location Optional Sender location, only for bots that require user location
 * @param inlineMessageId  String Optional Identifier of the sent inline message.
 *                         Available only if there is an inline keyboard attached to the message.
 *                         Will be also received in callback queries and can be used to edit the message.
 * @param query            String The query that was used to obtain the result
 */
case class ChosenInlineResult(
  resultId: String,
  from: User,
  location: Option[Location] = None,
  inlineMessageId: Option[String] = None,
  query: String
)

object ChosenInlineResult {
  implicit val customConfig: Configuration               = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[ChosenInlineResult] = deriveDecoder
  implicit val circeEncoder: Encoder[ChosenInlineResult] = deriveConfiguredEncoder
}
