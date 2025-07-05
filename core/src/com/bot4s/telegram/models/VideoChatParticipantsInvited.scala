package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents a service message about new members invited to a video chat.
 *
 * @param users  New members that were invited to the video chat
 */
case class VideoChatParticipantsInvited(
  users: Array[User]
)

object VideoChatParticipantsInvited {
  implicit val circeDecoder: Decoder[VideoChatParticipantsInvited] = deriveDecoder[VideoChatParticipantsInvited]
}
