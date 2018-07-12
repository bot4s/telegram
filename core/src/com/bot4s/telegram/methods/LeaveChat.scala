package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId

/** Use this method for your bot to leave a group, supergroup or channel. Returns True on success.
  *
  * @param chatId Integer or String Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
  */
case class LeaveChat(chatId: ChatId) extends JsonRequest[Boolean]
