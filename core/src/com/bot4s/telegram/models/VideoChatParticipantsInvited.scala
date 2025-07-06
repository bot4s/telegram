package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.extras.Configuration

/**
 * This object represents a service message about new members invited to a video chat.
 *
 * @param users  New members that were invited to the video chat
 */
case class VideoChatParticipantsInvited(
  users: Array[User]
)

object VideoChatParticipantsInvited {
  implicit val customConfig: Configuration                         = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[VideoChatParticipantsInvited] = deriveDecoder[VideoChatParticipantsInvited]
  implicit val circeEncoder: Encoder[VideoChatParticipantsInvited] = deriveConfiguredEncoder
}
