package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId
import com.bot4s.telegram.models.ChatInviteLink

/**
 * Use this method to create an additional invite link for a chat.
 * The bot must be an administrator in the chat for this to work and must have the appropriate administrator rights.
 * The link can be revoked using the method revokeChatInviteLink. Returns the new invite link as ChatInviteLink object.
 *
 * @param chatId 	            Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param name 	              String Invite link name; 0-32 characters
 * @param expireDate 	        Integer	Point in time (Unix timestamp) when the link will expire
 * @param memberLimit 	      Integer Maximum number of users that can be members of the chat simultaneously after joining the chat via this invite link; 1-99999
 * @param createsJoinRequest 	Boolean True, if users joining the chat via the link need to be approved by chat administrators. If True, member_limit can't be specified
 */
case class CreateChatInviteLink(
  chatId: ChatId,
  name: Option[String] = None,
  expireDate: Option[Int] = None,
  memberLimit: Option[Int] = None,
  createsJoinRequest: Option[Boolean] = None
) extends JsonRequest[ChatInviteLink] {

  if (createsJoinRequest.fold(false)(identity))
    require(memberLimit.isEmpty, "memberLimit can't be specified if createsJoinRequest is set")
}
