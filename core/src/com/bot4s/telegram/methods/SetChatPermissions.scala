package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ ChatId, ChatPermissions }

/**
 * Use this method to set default chat permissions for all members.
 * The bot must be an administrator in the group or a supergroup for this to work and must have the can_restrict_members admin rights.
 * Returns True on success.
 *
 * @param chatId      Integer or String Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 * @param permissions ChatPermissions New default chat permissions
 */
case class SetChatPermissions(chatId: ChatId, permissions: ChatPermissions) extends JsonRequest[Boolean]
