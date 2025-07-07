package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.extras.Configuration

/**
 * This object represents one shipping option.
 *
 * @param inlineMessageId     Optional. Identifier of the sent inline message. Available only if there is an inline keyboard attached to the message.
 */
case class SentWebAppMessage(
  inlineMessageId: Option[String] = None
)

object SentWebAppMessage {
  implicit val customConfig: Configuration              = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[SentWebAppMessage] = deriveDecoder
  implicit val circeEncoder: Encoder[SentWebAppMessage] = deriveConfiguredEncoder
}
