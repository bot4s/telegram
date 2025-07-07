package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ ChatId, ChatPermissions }
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to restrict a user in a supergroup.
 * The bot must be an administrator in the supergroup for this to work and must have the appropriate admin rights.
 * Pass True for all boolean parameters to lift restrictions from a user.
 * Returns True on success.
 *
 * @param chatId                        Integer or String	Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 * @param userId                        Long	Yes	Unique identifier of the target user
 * @param permissions                   ChatPermissions New user permissions
 * @param useIndependentChatPermissions Pass True if chat permissions are set independently.
 *                                      Otherwise, the can_send_other_messages and can_add_web_page_previews permissions will imply the can_send_messages, can_send_audios, can_send_documents, can_send_photos, can_send_videos, can_send_video_notes, and can_send_voice_notes permissions; the can_send_polls permission will imply the can_send_messages permission.
 * @param untilDate                     Integer Optional Date when restrictions will be lifted for the user, unix time.
 *                                      If user is restricted for more than 366 days or less than 30 seconds from the current time, they are considered to be restricted forever
 */
case class RestrictChatMember(
  chatId: ChatId,
  userId: Long,
  permissions: Option[ChatPermissions] = None,
  useIndependentChatPermissions: Option[Boolean] = None,
  untilDate: Option[Int] = None
) extends JsonRequest {
  type Response = Boolean
}

object RestrictChatMember {
  implicit val customConfig: Configuration                            = Configuration.default.withSnakeCaseMemberNames
  implicit val restrictChatMemberEncoder: Encoder[RestrictChatMember] = deriveConfiguredEncoder[RestrictChatMember]
}
