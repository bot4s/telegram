package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to set a custom title for an administrator in a supergroup promoted by the bot. Returns True on success.
 *
 * @param chat_id 	      Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 * @param user_id 	      Unique identifier of the target user
 * @param custom_title 	  New custom title for the administrator; 0-16 characters, emoji are not allowed
 */
case class SetChatAdministratorCustomTitle(
  chatId: ChatId,
  userId: Long,
  customTitle: String
) extends JsonRequest {
  type Response = Boolean
}

object SetChatAdministratorCustomTitle {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val setChatAdministratorCustomTitleEncoder: Encoder[SetChatAdministratorCustomTitle] =
    deriveConfiguredEncoder[SetChatAdministratorCustomTitle]
}
