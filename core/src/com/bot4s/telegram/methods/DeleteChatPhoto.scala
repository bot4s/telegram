package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId

/**
 * Use this method to delete a chat photo.
 * Photos can't be changed for private chats.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Returns True on success.
 *
 * '''Note:'''
 *   In regular groups (non-supergroups), this method will only work if the "All Members Are Admins" setting is off in the target group.
 *
 * @param chatId Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 */
case class DeleteChatPhoto(
  chatId: ChatId
) extends JsonRequest[Boolean]
