package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId

/**
 * Use this method to unban a previously kicked user in a supergroup.
 * The user will not return to the group automatically, but will be able to join via link, etc.
 * The bot must be an administrator in the group for this to work. Returns True on success.
 *
 * @param chatId        Integer or String Unique identifier for the target group or username of the target supergroup (in the format @supergroupusername)
 * @param userId        Long Unique identifier of the target user
 * @param onlyIfBanned  Do nothing if the user is not banned
 */
case class UnbanChatMember(
  chatId: ChatId,
  userId: Long,
  onlyIfBanned: Option[Boolean] = None
) extends JsonRequest[Boolean]
