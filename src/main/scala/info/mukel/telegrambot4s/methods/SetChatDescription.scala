package info.mukel.telegrambot4s.methods

/**
  * Use this method to change the description of a supergroup or a channel.
  * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
  * Returns True on success.
  *
  * @param chatId      Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param description String	No	New chat description, 0-255 characters pinChatMessage  *
  */
case class SetChatDescription(
                              chatId      : Either[Long, String],
                              description : Option[String] = None
                             ) extends ApiRequestJson[Boolean]
