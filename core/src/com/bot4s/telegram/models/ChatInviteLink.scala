package com.bot4s.telegram.models

/**
 * This object represents an invite link for a chat.
 *
 * @param creator                   Creator of the link
 * @param createsJoinRequest        True, if users joining the chat via the link need to be approved by chat administrators
 * @param expireDate                Optional. Point in time (Unix timestamp) when the link will expire or has been expired
 * @param inviteLink                The invite link. If the link was created by another chat administrator, then the second
 *                                  part of the link will be replaced with “…”.
 * @param isPrimary                 True, if the link is primary
 * @param isRevoked                 True, if the link is revoked
 * @param name                      Invite link name
 * @param memberLimit               Maximum number of users that can be members of the chat simultaneously after joining the chat
 *                                  via this invite link; 1-99999
 * @param pendingJoinRequestCount   Number of pending join requests created using this link
 */
case class ChatInviteLink(
  creator: User,
  createsJoinRequest: Boolean,
  expireDate: Option[Int] = None,
  inviteLink: String,
  isPrimary: Boolean,
  isRevoked: Boolean,
  name: Option[String] = None,
  memberLimit: Option[Int] = None,
  pendingJoinRequestCount: Option[Int] = None
)
