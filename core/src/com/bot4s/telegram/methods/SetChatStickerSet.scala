package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to set a new group sticker set for a supergroup.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Use the field can_set_sticker_set optionally returned in getChat requests to check if the bot can use this method. Returns True on success.
 *
 * @param chatId          Integer or String Yes	Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
 * @param stickerSetName  String Yes Name of the sticker set to be set as the group sticker set
 */
case class SetChatStickerSet(chatId: ChatId, stickerSetName: String) extends JsonRequest[Boolean]

object SetChatStickerSet {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val setChatStickerSetEncoder: Encoder[SetChatStickerSet] = deriveConfiguredEncoder[SetChatStickerSet]
}
