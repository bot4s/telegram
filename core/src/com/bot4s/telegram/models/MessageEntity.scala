package com.bot4s.telegram.models

import com.bot4s.telegram.models.MessageEntityType.MessageEntityType
import io.circe.{ Decoder, Encoder }
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.Configuration

/**
 * This object represents one special entity in a text message.
 *
 * For example, hashtags, usernames, URLs, etc.
 *
 * @param type            String Type of the entity.
 *                        One of mention (@username), hashtag, bot_command, url, email, bold (bold text), italic (italic text),
 *                        code (monowidth string), pre (monowidth block), text_link (for clickable text URLs),
 *                        text_mention (for users without usernames)
 * @param offset          Integer Offset in UTF-16 code units to the start of the entity
 * @param length          Integer Length of the entity in UTF-16 code units
 * @param url             String Optional For "text_link" only, url that will be opened after user taps on the text
 * @param user            User Optional. For "text_mention" only, the mentioned user
 * @param customEmojiId 	String 	Optional. For "custom_emoji" only, unique identifier of the custom emoji. Use getCustomEmojiStickers to get full information about the sticker
 */
case class MessageEntity(
  `type`: MessageEntityType,
  offset: Int,
  length: Int,
  url: Option[String] = None,
  user: Option[User] = None,
  customEmojiId: Option[String] = None
)

object MessageEntity {
  implicit val customConfig: Configuration          = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[MessageEntity] = deriveDecoder[MessageEntity]
  implicit val circeEncoder: Encoder[MessageEntity] = deriveConfiguredEncoder[MessageEntity]
}
