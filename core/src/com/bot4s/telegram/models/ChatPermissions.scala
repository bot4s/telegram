package com.bot4s.telegram.models

/**
 * Describes actions that a non-administrator user is allowed to take in a chat.
 *
 * @param canSendMessages        Boolean Optional. True, if the user is allowed to send text messages, contacts, locations and venues
 * @param canSendAudios,         Boolean Optional. True, if the user is allowed to send audios
 * @param canSendDocuments       Boolean Optional. True, if the user is allowed to send documents
 * @param canSendPhotos          Boolean Optional. True, if the user is allowed to send photos
 * @param canSendVideos          Boolean Optional. True, if the user is allowed to send videos
 * @param canSendVideoNotes      Boolean Optional. True, if the user is allowed to send video notes
 * @param canSendVoiceNotes      Boolean Optional. True, if the user is allowed to send voice notes
 * @param canSendPolls           Boolean Optional. True, if the user is allowed to send polls, implies can_send_messages
 * @param canSendOtherMessages   Boolean Optional. True, if the user is allowed to send animations, games, stickers and use inline bots, implies can_send_media_messages
 * @param canAddWebPagePreviews  Boolean Optional. True, if the user is allowed to add web page previews to their messages, implies can_send_media_messages
 * @param canChangeInfo          Boolean Optional. True, if the user is allowed to change the chat title, photo and other settings. Ignored in public supergroups
 * @param canInviteUsers         Boolean Optional. True, if the user is allowed to invite new users to the chat
 * @param canPinMessages         Boolean Optional. True, if the user is allowed to pin messages. Ignored in public supergroups
 * @param canManageTopics        Boolean Optional. True, if the user is allowed to created, rename, close, and reopen forum  topics; supergroups only
 */
case class ChatPermissions(
  canSendMessages: Option[Boolean] = None,
  canSendAudios: Option[Boolean] = None,
  canSendDocuments: Option[Boolean] = None,
  canSendPhotos: Option[Boolean] = None,
  canSendVideos: Option[Boolean] = None,
  canSendVideoNotes: Option[Boolean] = None,
  canSendVoiceNotes: Option[Boolean] = None,
  canSendPolls: Option[Boolean] = None,
  canSendOtherMessages: Option[Boolean] = None,
  canAddWebPagePreviews: Option[Boolean] = None,
  canChangeInfo: Option[Boolean] = None,
  canInviteUsers: Option[Boolean] = None,
  canPinMessages: Option[Boolean] = None,
  canManageTopics: Option[Boolean] = None
)
