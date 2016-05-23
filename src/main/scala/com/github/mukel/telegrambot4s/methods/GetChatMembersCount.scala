package com.github.mukel.telegrambot4s.methods

/** Use this method to get the number of members in a chat. Returns Int on success.
  *
  * @param chatId	Integer or String	Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
  */
case class GetChatMembersCount(chatId: Either[Long, String]) extends ApiRequestJson[Int]
