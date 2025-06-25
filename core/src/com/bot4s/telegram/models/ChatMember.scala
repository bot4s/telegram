package com.bot4s.telegram.models

/**
 * This object contains information about one member of the chat.
 *
 * @param user                   User Information about the user
 * @param status                 String The member's status in the chat. Can be "creator", "administrator", "member", "left" or "kicked"
 * @param untilDate              Integer Optional. Restricted and kicked only. Date when restrictions will be lifted for this user, unix time
 * @param canBeEdited            Boolean Optional. Administrators only. True, if the bot is allowed to edit administrator privileges of that user
 * @param canChangeInfo          Boolean Optional.  Administrators and restricted only. True, if the user is allowed to change the chat title, photo and other settings
 * @param canPostMessages        Boolean Optional. Administrators only. True, if the administrator can post in the channel, channels only
 * @param canEditMessages        Boolean Optional. Administrators only. True, if the administrator can edit messages of other users, channels only
 * @param canDeleteMessages      Boolean Optional. Administrators only. True, if the administrator can delete messages of other users
 * @param canInviteUsers         Boolean Optional. Administrators and restricted only. True, if the user is allowed to invite new users to the chat
 * @param canRestrictMembers     Boolean Optional. Administrators only. True, if the administrator can restrict, ban or unban chat members
 * @param canPinMessages         Boolean Optional. Administrators and restricted only. True, if the user is allowed to pin messages; groups and supergroups only
 * @param canPostStories         Boolean Optional. True, if the administrator can post stories in the channel; channels only
 * @param canEditStories         Boolean Optional. True, if the administrator can edit stories in the channel; channels only
 * @param canDeleteStories       Boolean Optional. True, if the administrator can delete stories in the channel; channels only
 * @param canPromoteMembers      Boolean Optional. Administrators only. True, if the administrator can add new administrators with a subset of his own privileges or demote administrators that he has promoted, directly or indirectly (promoted by administrators that were appointed by the user)
 * @param isMember               Boolean Optional. Restricted only. True, if the user is a member of the chat at the moment of the request
 * @param canSendMessages        Boolean Optional. Restricted only. True, if the user can send text messages, contacts, locations and venues
 * @param canSendAudios,         Boolean Optional. True, if the user is allowed to send audios
 * @param canSendDocuments       Boolean Optional. True, if the user is allowed to send documents
 * @param canSendPhotos          Boolean Optional. True, if the user is allowed to send photos
 * @param canSendVideos          Boolean Optional. True, if the user is allowed to send videos
 * @param canSendVideoNotes      Boolean Optional. True, if the user is allowed to send video notes
 * @param canSendVoiceNotes      Boolean Optional. True, if the user is allowed to send voice notes
 * @param canSendPolls           Boolean Optional. Restricted only. True, if the user is allowed to send polls
 * @param canSendOtherMessages   Boolean Optional. Restricted only. True, if the user can send animations, games, stickers and use inline bots, implies can_send_media_messages
 * @param canAddWebPagePreviews  Boolean Optional. Restricted only. True, if user may add web page previews to his messages, implies can_send_media_messages
 * @param canManageVideoChats    Boolean Optional. if the administrator can manage video chats
 * @param canManageTopics        Boolean Optional. True, if the user is allowed to created, rename, close, and reopen forum  topics; supergroups only
 */
case class ChatMember(
  user: User,
  status: MemberStatus,
  untilDate: Option[Int] = None,
  canBeEdited: Option[Boolean] = None,
  canChangeInfo: Option[Boolean] = None,
  canPostMessages: Option[Boolean] = None,
  canEditMessages: Option[Boolean] = None,
  canDeleteMessages: Option[Boolean] = None,
  canInviteUsers: Option[Boolean] = None,
  canRestrictMembers: Option[Boolean] = None,
  canPinMessages: Option[Boolean] = None,
  canPostStories: Option[Boolean] = None,
  canEditStories: Option[Boolean] = None,
  canDeleteStories: Option[Boolean] = None,
  canPromoteMembers: Option[Boolean] = None,
  isMember: Option[Boolean] = None,
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
  canManageVideoChats: Option[Boolean] = None,
  canManageTopics: Option[Boolean] = None
)
