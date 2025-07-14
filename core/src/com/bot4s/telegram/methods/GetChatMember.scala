package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ ChatId, ChatMember }
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to get information about a member of a chat. Returns a ChatMember object on success.
 *
 * @param chatId Integer or String Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 * @param userId Long Unique identifier of the target user
 */
case class GetChatMember(
  chatId: ChatId,
  userId: Long
) extends JsonRequest {
  type Response = ChatMember
}

object GetChatMember {
  implicit val customConfig: Configuration          = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[GetChatMember] = deriveConfiguredEncoder
}
