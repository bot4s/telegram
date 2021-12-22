package com.bot4s.telegram.models

/**
 * Represents a join request sent to a chat.
 *
 * @param chat          Chat to which the request was sent
 * @param from          User that sent the join request
 * @param date          Integer Date the request was sent in Unix time
 * @param bio           String Optional. Bio of the user.
 * @param inviteLink    ChatInviteLink Optional. Chat invite link that was used by the user to send the join request
 *                      via this invite link; 1-99999
 */
case class ChatJoinRequest(
  chat: Chat,
  from: User,
  date: Int,
  bio: Option[String] = None,
  inviteLink: Option[ChatInviteLink] = None
)
