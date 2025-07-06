package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to clear the list of pinned messages in a General forum topic.
 *
 * he bot must be an administrator in the chat for this to work and must have the can_pin_messages administrator right in the supergroup.
 * Returns True on success.
 *
 * @param chatId   ChatId  	Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 */
case class UnpinAllGeneralForumTopicMessages(chatId: ChatId) extends JsonRequest[Boolean]

object UnpinAllGeneralForumTopicMessages {
  implicit val customConfig: Configuration                              = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[UnpinAllGeneralForumTopicMessages] = deriveConfiguredEncoder
}
