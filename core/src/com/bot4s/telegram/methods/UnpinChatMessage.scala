package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to unpin a message in a supergroup chat.
 * The bot must be an administrator in the chat for this to work and must have the 'can_pin_messages' admin right in
 * the supergroup or 'can_edit_messages' admin right in the channel.
 * Returns True on success.
 *
 * @param chatId Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 */
case class UnpinChatMessage(
  chatId: ChatId
) extends JsonRequest[Boolean]

object UnpinChatMessage {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val unpinChatMessageEncoder: Encoder[UnpinChatMessage] = deriveConfiguredEncoder[UnpinChatMessage]
}
