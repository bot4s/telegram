package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId

/**
 * Use this method to close an open topic in a forum supergroup chat. The bot must be an administrator in the chat for this to work and must have the can_manage_topics administrator rights, unless it is the creator of the topic. Returns True on success.
 *
 * @param chatId              Integer or String Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 * @param messsageThreadId    Integer. Unique identifier for the target message thread of the forum topic
 */
case class CloseForumTopic(
  chatId: ChatId,
  messageThreadId: Int
) extends JsonRequest[Boolean]
