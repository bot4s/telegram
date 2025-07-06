package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ ChatId, ForumTopic }
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to create a topic in a forum supergroup chat. The bot must be an administrator in the chat for this to work and must have the can_manage_topics administrator rights. Returns information about the created topic as a ForumTopic object.
 *
 * @param chatId              Integer or String Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
 * @param name                String Topic name, 1-128 characters
 * @param iconColor          Color of the topic icon in RGB format. Currently, must be one of 7322096 (0x6FB9F0), 16766590 (0xFFD67E), 13338331 (0xCB86DB), 9367192 (0x8EEE98), 16749490 (0xFF93B2), or 16478047 (0xFB6F5F)
 * @param iconCustomEmojiId  	Unique identifier of the custom emoji shown as the topic icon. Use getForumTopicIconStickers to get all allowed custom emoji identifiers.
 */
case class CreateForumTopic(
  chatId: ChatId,
  name: String,
  iconColor: Int,
  iconCustomEmojiId: Option[String] = None
) extends JsonRequest[ForumTopic]

object CreateForumTopic {
  implicit val customConfig: Configuration             = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[CreateForumTopic] = deriveConfiguredEncoder
}
