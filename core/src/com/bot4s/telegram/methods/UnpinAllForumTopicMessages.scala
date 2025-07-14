package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to clear the list of pinned messages in a forum topic. The bot must be an administrator in the chat for this to work and must have the can_pin_messages administrator right in the supergroup. Returns True on success.
 *
 * @param chatId              Integer or String Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 * @param messsageThreadId    Integer. Unique identifier for the target message thread of the forum topic
 */
case class UnpinAllForumTopicMessages(
  chatId: ChatId,
  messageThreadId: Int
) extends JsonRequest {
  type Response = Boolean
}

object UnpinAllForumTopicMessages {
  implicit val customConfig: Configuration                       = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[UnpinAllForumTopicMessages] = deriveConfiguredEncoder
}
