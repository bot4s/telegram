package com.bot4s.telegram.methods

import com.bot4s.telegram.models.BotCommandScope.BotCommandScope

/**
 *  Use this method to delete the list of the bot's commands for the given scope and user language. After deletion, higher level commands will be shown to affected users. Returns True on success.
 *    @param scope 	        BotCommandScope Optional
 *                          A JSON-serialized object, describing scope of users for which the commands are relevant. Defaults to BotCommandScopeDefault.
 *    @param  languageCode 	String 	Optional
 *                          A two-letter ISO 639-1 language code. If empty, commands will be applied to all users from the given scope, for whose language there are no dedicated commands
 */
case class DeleteMyCommands(
  scope: Option[BotCommandScope] = None,
  languageCode: Option[String] = None
) extends JsonRequest[Boolean]
