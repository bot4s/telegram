package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to delete a message.
 *
 * A message can only be deleted if it was sent less than 48 hours ago.
 * Any such recently sent outgoing message may be deleted.
 * Additionally, if the bot is an administrator in a group chat, it can delete any message.
 * If the bot is an administrator in a supergroup, it can delete messages from any other user
 * and service messages about people joining or leaving the group
 * (other types of service messages may only be removed by the group creator).
 * In channels, bots can only remove their own messages. Returns True on success.
 *
 * @param chatId     Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId  Integer Identifier of the message to delete
 */
case class DeleteMessage(chatId: ChatId, messageId: Int) extends JsonRequest {
  type Response = Boolean
}

object DeleteMessage {
  implicit val customConfig: Configuration          = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[DeleteMessage] = deriveConfiguredEncoder
}
