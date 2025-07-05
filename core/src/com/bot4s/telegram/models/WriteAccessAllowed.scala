package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents a service message about a user allowing a bot added to the attachment menu to write messages.
 * @param fromRequest         True, if the access was granted after the user accepted an explicit request from a Web App sent by the method requestWriteAccess
 * @param webAppName          Name of the Web App which was launched from a link
 * @param fromAttachmentMenu  True, if the access was granted when the bot was added to the attachment or side menu
 */
case class WriteAccessAllowed(
  fromRequest: Option[Boolean] = None,
  webAppName: Option[String] = None,
  fromAttachmentMenu: Option[Boolean] = None
)

object WriteAccessAllowed {
  implicit val circeDecoder: Decoder[WriteAccessAllowed] = deriveDecoder[WriteAccessAllowed]
}
