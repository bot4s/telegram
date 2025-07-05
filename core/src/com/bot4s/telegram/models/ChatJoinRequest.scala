package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * Represents a join request sent to a chat.
 *
 * @param chat          Chat to which the request was sent
 * @param from          User that sent the join request
 * @param userChatId    Identifier of a private chat with the user who sent the join request
 * @param date          Integer Date the request was sent in Unix time
 * @param bio           String Optional. Bio of the user.
 * @param inviteLink    ChatInviteLink Optional. Chat invite link that was used by the user to send the join request
 *                      via this invite link; 1-99999
 */
case class ChatJoinRequest(
  chat: Chat,
  from: User,
  userChatId: Long,
  date: Int,
  bio: Option[String] = None,
  inviteLink: Option[ChatInviteLink] = None
)

object ChatJoinRequest {
  implicit val circeDecoder: Decoder[ChatJoinRequest] = deriveDecoder[ChatJoinRequest]
}
