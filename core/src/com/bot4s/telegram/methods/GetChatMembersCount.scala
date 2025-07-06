package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to get the number of members in a chat. Returns Int on success.
 *
 * @param chatId Integer or String Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 */
case class GetChatMembersCount(chatId: ChatId) extends JsonRequest[Int]

object GetChatMembersCount {
  implicit val customConfig: Configuration                = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[GetChatMembersCount] = deriveConfiguredEncoder
}
