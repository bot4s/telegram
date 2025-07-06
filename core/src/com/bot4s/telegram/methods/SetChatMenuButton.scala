package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ ChatId, MenuButton, MenuButtonDefault }
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to change the bot's menu button in a private chat, or the default menu button. Returns True on success.
 *
 * @param chatId 	      Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 * @param menuButton 	  A JSON-serialized object for the bot's new menu button. Defaults to MenuButtonDefault
 */
case class SetChatMenuButton(
  chatId: ChatId,
  menuButton: Option[MenuButton] = Some(MenuButtonDefault())
) extends JsonRequest[Boolean]

object SetChatMenuButton {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val setChatButtonEncoder: Encoder[SetChatMenuButton] = deriveConfiguredEncoder[SetChatMenuButton]
}
