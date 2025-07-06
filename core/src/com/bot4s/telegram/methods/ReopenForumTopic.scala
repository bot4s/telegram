package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to reopen a closed topic in a forum supergroup chat. The bot must be an administrator in the chat for this to work and must have the can_manage_topics administrator rights, unless it is the creator of the topic. Returns True on success.
 *
 * @param chatId              Integer or String Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 * @param messsageThreadId    Integer. Unique identifier for the target message thread of the forum topic
 */
case class ReopenForumTopic(
  chatId: ChatId,
  messageThreadId: Int
) extends JsonRequest[Boolean]

object ReopenForumTopic {
  implicit val customConfig: Configuration             = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[ReopenForumTopic] = deriveConfiguredEncoder
}
