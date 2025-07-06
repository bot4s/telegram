package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to pin a message in a supergroup.
 * The bot must be an administrator in the chat for this to work and must have the 'can_pin_messages' admin right in
 * the supergroup or 'can_edit_messages' admin right in the channel.
 * Returns True on success.
 *
 * @param chatId               Integer or String	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId            Integer	Identifier of a message to pin
 * @param disableNotification  Boolean	Optional Pass True, if it is not necessary to send a notification to all chat members about the new pinned message.
 *                             Notifications are always disabled in channels.
 */
case class PinChatMessage(
  chatId: ChatId,
  messageId: Int,
  disableNotification: Option[Boolean] = None
) extends JsonRequest[Boolean]

object PinChatMessage {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val pinChatMessageEncoder: Encoder[PinChatMessage] = deriveConfiguredEncoder[PinChatMessage]
}
