package info.mukel.telegram.bots.v2.methods

import info.mukel.telegram.bots.v2.model.ChatMember

/** Use this method to get a list of administrators in a chat.
  * On success, returns an Array of ChatMember objects that contains information about all chat administrators except other bots.
  * If the chat is a group or a supergroup and no administrators were appointed, only the creator will be returned.
  *
  * @param chatId	Integer or String	Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
  */
case class GetChatAdministrators(chatId: Either[Long, String]) extends ApiRequestJson[Seq[ChatMember]]
