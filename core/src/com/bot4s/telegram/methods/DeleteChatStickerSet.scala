package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to delete a group sticker set from a supergroup.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Use the field can_set_sticker_set optionally returned in getChat requests to check if the bot can use this method.
 * Returns True on success.
 *
 * @param chatId Integer or String Yes Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 */
case class DeleteChatStickerSet(chatId: ChatId) extends JsonRequest[Boolean]

object DeleteChatStickerSet {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val deleteChatStickerSetEncoder: Encoder[DeleteChatStickerSet] = deriveConfiguredEncoder[DeleteChatStickerSet]
}
