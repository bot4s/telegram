package com.bot4s.telegram.models

/**
 * This object represents a service message about new members invited to a video chat.
 *
 * @param users  New members that were invited to the video chat
 */
case class VideoChatParticipantsInvited(
  users: Array[User]
)
