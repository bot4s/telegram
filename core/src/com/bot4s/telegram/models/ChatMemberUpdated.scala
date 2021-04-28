package com.bot4s.telegram.models

/**
 * This object represents changes in the status of a chat member.
 *
 * @param from                   Performer of the action, which resulted in the change
 * @param date                   Date the change was done in Unix time
 * @param chat                   Chat the user belongs to
 * @param oldChatMember          Previous information about the chat member
 * @param newChatMember          New information about the chat member
 * @param inviteLink             Optional. Chat invite link, which was used by the user to join the chat; for joining by invite link events only.
 */
case class ChatMemberUpdated(
  from: User,
  date: Int,
  chat: Chat,
  oldChatMember: ChatMember,
  newChatMember: ChatMember,
  inviteLink: Option[ChatInviteLink] = None
)
