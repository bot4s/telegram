package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ChatId, ChatPermissions}

/**
  * Use this method to restrict a user in a supergroup.
  * The bot must be an administrator in the supergroup for this to work and must have the appropriate admin rights.
  * Pass True for all boolean parameters to lift restrictions from a user.
  * Returns True on success.
  *
  * @param chatId                 Integer or String	Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
  * @param userId                 Integer	Yes	Unique identifier of the target user
  * @param permissions            ChatPermissions New user permissions
  * @param untilDate              Integer Optional Date when restrictions will be lifted for the user, unix time.
  *                               If user is restricted for more than 366 days or less than 30 seconds from the current time, they are considered to be restricted forever
  * @param canSendMessages        Boolean	Optional Pass True, if the user can send text messages, contacts, locations and venues
  * @param canSendMediaMessages   Boolean	Optional Pass True, if the user can send audios, documents, photos, videos, video notes and voice notes, implies can_send_messages
  * @param canSendOtherMessages   Boolean	Optional Pass True, if the user can send animations, games, stickers and use inline bots, implies can_send_media_messages
  * @param canAddWebPagePreviews  Boolean Optional Pass True, if the user may add web page previews to their messages, implies can_send_media_messages
  */
case class RestrictChatMember(
                             chatId                : ChatId,
                             userId                : Long,
                             permissions           : Option[ChatPermissions] = None,
                             untilDate             : Option[Int] = None,
                             @Deprecated
                             canSendMessages       : Option[Boolean] = None,
                             @Deprecated
                             canSendMediaMessages  : Option[Boolean] = None,
                             @Deprecated
                             canSendOtherMessages  : Option[Boolean] = None,
                             @Deprecated
                             canAddWebPagePreviews : Option[Boolean] = None
                             ) extends JsonRequest[Boolean]
