package info.mukel.telegrambot4s.methods

/**
  * Use this method to unpin a message in a supergroup chat.
  * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
  * Returns True on success.
  *
  * @param chatId Integer or String Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
  */
case class UnpinChatMessage(
                             chatId: Either[Long, String]
                           ) extends ApiRequestJson[Boolean]
