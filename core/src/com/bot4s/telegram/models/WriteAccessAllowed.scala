package com.bot4s.telegram.models

/**
 * This object represents a service message about a user allowing a bot added to the attachment menu to write messages. Currently holds no information.
 * @param webAppName  Name of the Web App which was launched from a link
 */
case class WriteAccessAllowed(
  webAppName: Option[String] = None
)
