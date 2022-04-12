package com.bot4s.telegram.models

/**
 * This object represents a bot command.
 *
 *  @param command 	String 	Text of the command, 1-32 characters. Can contain only lowercase English letters, digits and underscores.
 *  @param description 	String 	Description of the command, 1-256 characters.
 */
case class BotCommand private (
  command: String,
  description: String
)

object BotCommand {
  def apply(command: String, description: String) =
    new BotCommand(command.toLowerCase().take(32), description.take(256))
}
