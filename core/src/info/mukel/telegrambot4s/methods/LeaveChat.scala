package info.mukel.telegrambot4s.methods

import info.mukel.telegrambot4s.models.ChatId

/** Use this method for your bot to leave a group, supergroup or channel. Returns True on success.
  *
  * @param chatId Integer or String Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
  */
case class LeaveChat(chatId: ChatId) extends ApiRequestJson[Boolean]
