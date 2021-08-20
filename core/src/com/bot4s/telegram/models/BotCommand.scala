package com.bot4s.telegram.models

/**
 * This object represents a bot command.
 *
 *  @param command 	String 	Text of the command, 1-32 characters. Can contain only lowercase English letters, digits and underscores.
 *  @param description 	String 	Description of the command, 3-256 characters.
 */
case class BotCommand(
  command: String,
  description: String
)
