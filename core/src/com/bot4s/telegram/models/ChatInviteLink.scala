package com.bot4s.telegram.models

/** This object represents an invite link for a chat.
  *
  * @param creator                Creator of the link
  * @param expireDate             Optional. Point in time (Unix timestamp) when the link will expire or has been expired
  * @param inviteLink             The invite link. If the link was created by another chat administrator, then the second
  *                               part of the link will be replaced with “…”.
  * @param isPrimary              True, if the link is primary
  * @param isRevoked              True, if the link is revoked
  * @param memberLimit            Optional. Maximum number of users that can be members of the chat simultaneously after joining the chat
  *                               via this invite link; 1-99999
  */
case class  ChatInviteLink(
               creator               : User,
               expireDate            : Int,
               inviteLink            : String,
               isPrimary             : Boolean,
               isRevoked             : Boolean,
               memberLimit           : ChatMember
                   ) {
}
