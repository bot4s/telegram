package com.bot4s.telegram.models

/**
 * Describes actions that a non-administrator user is allowed to take in a chat.
 *
 * @param canSendMessages        Boolean Optional. True, if the user is allowed to send text messages, contacts, locations and venues
 * @param canSendMediaMessages   Boolean Optional. True, if the user is allowed to send audios, documents, photos, videos, video notes and voice notes, implies can_send_messages
 * @param canSendPolls           Boolean Optional. True, if the user is allowed to send polls, implies can_send_messages
 * @param canSendOtherMessages   Boolean Optional. True, if the user is allowed to send animations, games, stickers and use inline bots, implies can_send_media_messages
 * @param canAddWebPagePreviews  Boolean Optional. True, if the user is allowed to add web page previews to their messages, implies can_send_media_messages
 * @param canChangeInfo          Boolean Optional. True, if the user is allowed to change the chat title, photo and other settings. Ignored in public supergroups
 * @param canInviteUsers         Boolean Optional. True, if the user is allowed to invite new users to the chat
 * @param canPinMessages         Boolean Optional. True, if the user is allowed to pin messages. Ignored in public supergroups
 */
case class ChatPermissions(
  canSendMessages: Option[Boolean] = None,
  canSendMediaMessages: Option[Boolean] = None,
  canSendPolls: Option[Boolean] = None,
  canSendOtherMessages: Option[Boolean] = None,
  canAddWebPagePreviews: Option[Boolean] = None,
  canChangeInfo: Option[Boolean] = None,
  canInviteUsers: Option[Boolean] = None,
  canPinMessages: Option[Boolean] = None
)
