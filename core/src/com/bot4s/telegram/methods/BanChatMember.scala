package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId

/**
 * Use this method to ban a user from a group, a supergroup or a channel.
 * In the case of supergroups and channels, the user will not be able to return to the group on their own using invite links, etc., unless unbanned first.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Returns True on success.
 *
 * '''Note:'''
 *   In regular groups (non-supergroups), this method will only work if the "All Members Are Admins" setting is off in the target group.
 *   Otherwise members may only be removed by the group's creator or by the member that added them.
 *
 * @param chatId          Integer or String Unique identifier for the target group or username of the target supergroup (in the format @supergroupusername)
 * @param userId          Long Unique identifier of the target user
 * @param untilDate       Integer Optional Date when the user will be unbanned, unix time.
 *                        If user is banned for more than 366 days or less than 30 seconds from the current time they are considered to be banned forever
 * @param revokeMessages  Boolean Optional Pass True to delete all messages from the chat for the user that is being removed.
 *                        If False, the user will be able to see messages in the group that were sent before the user was removed.
 *                        Always True for supergroups and channels.
 */
case class BanChatMember(
  chatId: ChatId,
  userId: Long,
  untilDate: Option[Int] = None,
  revokeMessages: Option[Boolean] = None
) extends JsonRequest[Boolean]
