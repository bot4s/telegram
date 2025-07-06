package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ ChatId, MenuButton }
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to get the current value of the bot's menu button in a private chat, or the default menu button.
 * Returns MenuButton on success.
 *
 * @param chatId      Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 */
case class GetChatMenuButton(
  chatId: ChatId
) extends JsonRequest {
  type Response = MenuButton
}

object GetChatMenuButton {
  implicit val customConfig: Configuration              = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[GetChatMenuButton] = deriveConfiguredEncoder
}
