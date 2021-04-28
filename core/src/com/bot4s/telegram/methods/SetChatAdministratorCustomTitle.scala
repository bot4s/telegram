package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId

/**
 * Use this method to set a custom title for an administrator in a supergroup promoted by the bot. Returns True on success.
 *
 * @param chat_id 	      Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 * @param user_id 	      Unique identifier of the target user
 * @param custom_title 	New custom title for the administrator; 0-16 characters, emoji are not allowed
 */
case class SetChatAdministratorCustomTitle(
  chatId: ChatId,
  userId: Int,
  customTitle: String
) extends JsonRequest[Boolean]
