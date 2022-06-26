package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ ChatId, MenuButton }

/**
 * Use this method to get the current value of the bot's menu button in a private chat, or the default menu button.
 * Returns MenuButton on success.
 *
 * @param chatId      Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 */
case class GetChatMenuButton(
  chatId: ChatId
) extends JsonRequest[MenuButton]
