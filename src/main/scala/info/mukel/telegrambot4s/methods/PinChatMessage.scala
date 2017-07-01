package info.mukel.telegrambot4s.methods

/**
  * Use this method to pin a message in a supergroup.
  * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
  * Returns True on success.
  *
  * @param chatId               Integer or String	Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
  * @param messageId            Integer	Identifier of a message to pin
  * @param disableNotification  Boolean	Optional Pass True, if it is not necessary to send a notification to all group members about the new pinned message
  */
case class PinChatMessage(
                           chatId              : Either[Long, String],
                           messageId           : Long,
                           disableNotification : Option[Boolean] = None
                         ) extends ApiRequestJson[Boolean]
