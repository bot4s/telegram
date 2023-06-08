package com.bot4s.telegram.models

import com.bot4s.telegram.models.StickerType.StickerType

/**
 * This object represents a sticker.
 *
 * @param fileId           Identifier for this file
 * @param fileUniqueId     Unique identifier for this file
 * @param type             Type of the sticker, currently one of “regular”, “mask”, “custom_emoji”.
 *                          The type of the sticker is independent from its format, which is determined by the fields is_animated and is_video.
 * @param width            Sticker width
 * @param height           Sticker height
 * @param isAnimated       Boolean True, if the sticker is animated
 * @param isVideo          Boolean True, if the sticker is a video sticker
 * @param thumbnail        Optional Sticker thumbnail in .webp or .jpg format
 * @param emoji            Optional. Emoji associated with the sticker
 * @param setName          String Optional. Name of the sticker set to which the sticker belongs
 * @param maskPosition	   MaskPosition	Optional.
 *                         For mask stickers, the position where the mask should be placed
 * @param fileSize         Integer Optional. File size
 * @param premiumAnimation File Optional. Premium animation for the sticker, if the sticker is premium
 * @param customEmojiId    String Optional. For custom emoji stickers, unique identifier of the custom emoji
 * @param needsRepainting  True, if the sticker must be repainted to a text color in messages, the color of the Telegram Premium badge in emoji status, white color on chat photos, or another appropriate color in other places
 */
case class Sticker(
  fileId: String,
  fileUniqueId: String,
  `type`: StickerType,
  width: Int,
  height: Int,
  isAnimated: Boolean,
  isVideo: Boolean,
  thumbnail: Option[PhotoSize] = None,
  emoji: Option[String] = None,
  setName: Option[String] = None,
  maskPosition: Option[MaskPosition] = None,
  fileSize: Option[Int] = None,
  premiumAnimation: Option[File] = None,
  customEmojiId: Option[String] = None,
  needsRepainting: Option[Boolean] = None
)
