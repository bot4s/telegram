package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ ChatId, ChatPermissions }
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to set default chat permissions for all members.
 * The bot must be an administrator in the group or a supergroup for this to work and must have the can_restrict_members admin rights.
 * Returns True on success.
 *
 * @param chatId      Integer or String Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 * @param permissions ChatPermissions New default chat permissions
 * @param useIndependentChatPermissions Pass True if chat permissions are set independently.
 *                                      Otherwise, the can_send_other_messages and can_add_web_page_previews permissions will imply the can_send_messages, can_send_audios, can_send_documents, can_send_photos, can_send_videos, can_send_video_notes, and can_send_voice_notes permissions; the can_send_polls permission will imply the can_send_messages permission.
 */
case class SetChatPermissions(
  chatId: ChatId,
  permissions: ChatPermissions,
  useIndependentChatPermissions: Option[Boolean] = None
) extends JsonRequest {
  type Response = Boolean
}

object SetChatPermissions {
  implicit val customConfig: Configuration                            = Configuration.default.withSnakeCaseMemberNames
  implicit val setChatPermissionsEncoder: Encoder[SetChatPermissions] = deriveConfiguredEncoder[SetChatPermissions]
}
