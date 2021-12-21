package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId

/**
 * Use this method to approve a chat join request.
 * The bot must be an administrator in the chat for this to work and must have the can_invite_users administrator right.
 * Returns True on success.
 *
 * @param chatId 	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param userId 	Long Unique identifier of the target user
 */
case class ApproveChatJoinRequest(
  chatId: ChatId,
  userId: Long
) extends JsonRequest[Boolean]
