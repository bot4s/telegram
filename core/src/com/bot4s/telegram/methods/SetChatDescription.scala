package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to change the description of a supergroup or a channel.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Returns True on success.
 *
 * @param chatId      Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param description String	No	New chat description, 0-255 characters pinChatMessage  *
 */
case class SetChatDescription(
  chatId: ChatId,
  description: Option[String] = None
) extends JsonRequest {
  type Response = Boolean
}

object SetChatDescription {
  implicit val customConfig: Configuration                            = Configuration.default.withSnakeCaseMemberNames
  implicit val setChatDescriptionEncoder: Encoder[SetChatDescription] = deriveConfiguredEncoder[SetChatDescription]
}
