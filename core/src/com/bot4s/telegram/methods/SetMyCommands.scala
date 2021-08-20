package com.bot4s.telegram.methods

import com.bot4s.telegram.models.BotCommand
import com.bot4s.telegram.models.BotCommandScope.BotCommandScope

/**
 *  Use this method to change the list of the bot's commands
 *    @param commands 	    Array of BotCommand
 *                          List of bot commands to be set as the list of the bot's commands. At most 100 commands can be specified.
 *    @param scope 	        BotCommandScope Optional
 *                          A JSON-serialized object, describing scope of users for which the commands are relevant. Defaults to BotCommandScopeDefault.
 *    @param  languageCode 	String 	Optional
 *                          A two-letter ISO 639-1 language code. If empty, commands will be applied to all users from the given scope, for whose language there are no dedicated commands
 */
case class SetMyCommands(
  commands: List[BotCommand],
  scope: Option[BotCommandScope],
  languageCode: Option[String]
) extends JsonRequest[Boolean]
