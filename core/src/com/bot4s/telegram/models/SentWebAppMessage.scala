package com.bot4s.telegram.models

/**
 * This object represents one shipping option.
 *
 * @param inlineMessageId     Optional. Identifier of the sent inline message. Available only if there is an inline keyboard attached to the message.
 */
case class SentWebAppMessage(
  inlineMessageId: Option[String] = None
)
