package com.bot4s.telegram.methods

import com.bot4s.telegram.models.BotCommandScope.BotCommandScope
import com.bot4s.telegram.models.BotCommand
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 *  Use this method to get the current list of the bot's commands for the given scope and user language. Returns Array of BotCommand on success. If commands aren't set, an empty list is returned.
 *    @param scope 	        BotCommandScope Optional
 *                          A JSON-serialized object, describing scope of users for which the commands are relevant. Defaults to BotCommandScopeDefault.
 *    @param  languageCode 	String 	Optional
 *                          A two-letter ISO 639-1 language code. If empty, commands will be applied to all users from the given scope, for whose language there are no dedicated commands
 */
case class GetMyCommands(
  scope: Option[BotCommandScope] = None,
  languageCode: Option[String] = None
) extends JsonRequest {
  type Response = List[BotCommand]
}

object GetMyCommands {
  implicit val customConfig: Configuration                  = Configuration.default.withSnakeCaseMemberNames
  implicit val getMyCommandsEncoder: Encoder[GetMyCommands] = deriveConfiguredEncoder[GetMyCommands]
}
