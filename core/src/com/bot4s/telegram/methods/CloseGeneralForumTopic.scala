package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId

/**
 * Use this method to close an open 'General' topic in a forum supergroup chat. The bot must be an administrator in the chat for this to work and must have the can_manage_topics administrator rights. Returns True on success.
 *
 * @param chatId    Integer or String Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 */
case class CloseGeneralForumTopic(
  chatId: ChatId
) extends JsonRequest[Boolean]
