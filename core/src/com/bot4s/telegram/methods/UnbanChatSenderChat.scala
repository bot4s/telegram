package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId

/**
 * Use this method to unban a previously banned channel chat in a supergroup or channel.
 * The bot must be an administrator for this to work and must have the appropriate administrator rights.
 * Returns True on success.
 *
 * @param chatId        Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param senderChatId  Unique identifier of the target sender chat
 */
case class UnbanChatSenderChat(
  chatId: ChatId,
  sendChatId: Long
) extends JsonRequest[Boolean]
