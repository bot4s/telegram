package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents one shipping option.
 *
 * @param inlineMessageId     Optional. Identifier of the sent inline message. Available only if there is an inline keyboard attached to the message.
 */
case class SentWebAppMessage(
  inlineMessageId: Option[String] = None
)

object SentWebAppMessage {
  implicit val circeDecoder: Decoder[SentWebAppMessage] = deriveDecoder[SentWebAppMessage]
}
