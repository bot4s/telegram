package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.extras.Configuration

/**
 * Represents the rights of an administrator in a chat.
 *
 * @param isAnonymous         Boolean True, if the user's presence in the chat is hidden
 * @param canManageChat       Boolean True, if the administrator can access the chat event log, chat statistics, message statistics in channels, see channel members, see anonymous administrators in supergroups and ignore slow mode. Implied by any other administrator privilege
 * @param canDeleteMessages   Boolean True, if the administrator can delete messages of other users
 * @param canManageVideoChats Boolean True, if the administrator can manage video chats
 * @param canRestrictMembers  Boolean True, if the administrator can restrict, ban or unban chat members
 * @param canPromoteMembers   Boolean True, if the administrator can add new administrators with a subset of their own privileges or demote administrators that he has promoted, directly or indirectly (promoted by administrators that were appointed by the user)
 * @param canChangeInfo       Boolean True, if the user is allowed to change the chat title, photo and other settings
 * @param canInviteUsers      Boolean True, if the user is allowed to invite new users to the chat
 * @param canPostMessages     Boolean Optional. True, if the administrator can post in the channel; channels only
 * @param canEditMessages     Boolean Optional. True, if the administrator can edit messages of other users and can pin messages; channels only
 * @param canPinMessages      Boolean Optional. True, if the user is allowed to pin messages; groups and supergroups only
 * @param from                Performer of the action, which resulted in the change
 * @param date                Date the change was done in Unix time
 * @param chat                Chat the user belongs to
 * @param oldChatMember       Previous information about the chat member
 * @param newChatMember       New information about the chat member
 * @param inviteLink          Optional. Chat invite link, which was used by the user to join the chat; for joining by invite link events only.
 * @param canManageTopics     Boolean Optional. True, if the user is allowed to created, rename, close, and reopen forum  topics; supergroups only
 */
case class ChatAdministratorRights(
  isAnonymous: Boolean,
  canManageChat: Boolean,
  canDeleteMessages: Boolean,
  canManageVideoChats: Boolean,
  canRestrictMembers: Boolean,
  canPromoteMembers: Boolean,
  canChangeInfo: Boolean,
  canInviteUsers: Boolean,
  canPostMessages: Option[Boolean],
  canEditMessages: Option[Boolean],
  canPinMessages: Option[Boolean],
  canManageTopics: Option[Boolean]
)

object ChatAdministratorRights {
  implicit val customConfig: Configuration                    = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[ChatAdministratorRights] = deriveDecoder
  implicit val circeEncoder: Encoder[ChatAdministratorRights] =
    deriveConfiguredEncoder
}
