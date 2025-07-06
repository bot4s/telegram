package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ Chat, ChatId }
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to get up to date information about the chat (current name of the user for one-on-one conversations, current username of a user, group or channel, etc.).
 * Returns a Chat object on success.
 *
 * @param chatId Integer or String Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 */
case class GetChat(chatId: ChatId) extends JsonRequest {
  type Response = Chat
}

object GetChat {
  implicit val customConfig: Configuration    = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[GetChat] = deriveConfiguredEncoder
}
