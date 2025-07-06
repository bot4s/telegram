package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * This object represents changes in the status of a chat member.
 *
 * @param from                    Performer of the action, which resulted in the change
 * @param date                    Date the change was done in Unix time
 * @param chat                    Chat the user belongs to
 * @param oldChatMember           Previous information about the chat member
 * @param newChatMember           New information about the chat member
 * @param inviteLink              Chat invite link, which was used by the user to join the chat; for joining by invite link events only.
 * @param viaChatFolderInviteLink True, if the user joined the chat via a chat folder invite link
 */
case class ChatMemberUpdated(
  from: User,
  date: Int,
  chat: Chat,
  oldChatMember: ChatMember,
  newChatMember: ChatMember,
  inviteLink: Option[ChatInviteLink] = None,
  viaChatFolderInviteLink: Option[Boolean] = None
)

object ChatMemberUpdated {
  implicit val customConfig: Configuration              = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[ChatMemberUpdated] = deriveDecoder[ChatMemberUpdated]
  implicit val circeEncoder: Encoder[ChatMemberUpdated] = deriveConfiguredEncoder[ChatMemberUpdated]
}
