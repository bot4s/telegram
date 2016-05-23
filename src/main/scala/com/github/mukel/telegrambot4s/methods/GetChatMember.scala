package com.github.mukel.telegrambot4s.methods

import com.github.mukel.telegrambot4s.models.ChatMember

/** Use this method to get information about a member of a chat. Returns a ChatMember object on success.
  *
  * @param chatId	Integer or String	Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
  * @param userId	Integer	Unique identifier of the target user
  */
case class GetChatMember(
                          chatId : Either[Long, String],
                          userId : Long
                        ) extends ApiRequestJson[ChatMember]
