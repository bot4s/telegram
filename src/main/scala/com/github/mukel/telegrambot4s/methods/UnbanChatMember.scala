package com.github.mukel.telegrambot4s.methods

/** Use this method to unban a previously kicked user in a supergroup.
  * The user will not return to the group automatically, but will be able to join via link, etc.
  * The bot must be an administrator in the group for this to work. Returns True on success.
  *
  * @param chatId	Integer or String	Unique identifier for the target group or username of the target supergroup (in the format @supergroupusername)
  * @param userId	Integer	Unique identifier of the target user
  */
case class UnbanChatMember(
                          chatId: Either[Long, String],
                          userId: Long
                          ) extends ApiRequestJson[Boolean]
