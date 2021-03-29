package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId

/**
  * Use this method to promote or demote a user in a supergroup or a channel.
  * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
  * Pass False for all boolean parameters to demote a user.
  * Returns True on success.
 *
 * @param chatId              Integer or String Unique identifier for the target chat or username of the target channel
  *                            (in the format @channelusername)
  * @param userId              Integer Unique identifier of the target user
  * @param canChangeInfo       Boolean Optional Pass True, if the administrator can change chat title, photo and other settings
  * @param canPostMessages     Boolean Optional Pass True, if the administrator can create channel posts, channels only
  * @param canEditMessages     Boolean Optional Pass True, if the administrator can edit messages of other users, channels only
  * @param canDeleteMessages   Boolean Optional Pass True, if the administrator can delete messages of other users
  * @param canInviteUsers      Boolean Optional Pass True, if the administrator can invite new users to the chat
  * @param canRestrictMembers  Boolean Optional Pass True, if the administrator can restrict, ban or unban chat members
  * @param canPinMessages      Boolean Optional Pass True, if the administrator can pin messages, supergroups only
  * @param canPromoteMembers   Boolean Optional Pass True, if the administrator can add new administrators with a subset 
  *                            of his own privileges or demote administrators that he has promoted, 
  *                            directly or indirectly (promoted by administrators that were appointed by him)
  */
case class PromoteChatMember(
                            chatId             : ChatId,
                            userId             : Long,
                            canChangeInfo      : Option[Boolean] = None,
                            canPostMessages    : Option[Boolean] = None,
                            canEditMessages    : Option[Boolean] = None,
                            canDeleteMessages  : Option[Boolean] = None,
                            canInviteUsers     : Option[Boolean] = None,
                            canRestrictMembers : Option[Boolean] = None,
                            canPinMessages     : Option[Boolean] = None,
                            canPromoteMembers  : Option[Boolean] = None
                            ) extends JsonRequest[Boolean]

