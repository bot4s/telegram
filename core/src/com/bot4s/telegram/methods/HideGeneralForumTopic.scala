package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to hide the 'General' topic in a forum supergroup chat.
 * The bot must be an administrator in the chat for this to work and must have the can_manage_topics administrator rights. The topic will be automatically closed if it was open. Returns True on success.
 *
 * @param chatId    Integer or String Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 */
case class HideGeneralForumTopic(
  chatId: ChatId
) extends JsonRequest[Boolean]

object HideGeneralForumTopic {
  implicit val customConfig: Configuration                  = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[HideGeneralForumTopic] = deriveConfiguredEncoder
}
