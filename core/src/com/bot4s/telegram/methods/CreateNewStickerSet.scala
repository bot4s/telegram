package com.bot4s.telegram.methods

import com.bot4s.telegram.models.MaskPosition
import com.bot4s.telegram.models.InputFile
import com.bot4s.telegram.models.StickerType.StickerType

/**
 * Use this method to create new sticker set owned by a user.
 * The bot will be able to edit the created sticker set.
 * Returns True on success.
 *
 * @param userId         Long User identifier of created sticker set owner
 * @param name           String Short name of sticker set, to be used in t.me/addstickers/ URLs (e.g., animals). Can contain only english letters, digits and underscores. Must begin with a letter, can't contain consecutive underscores and must end in “_by_<bot username>”. <bot_username> is case insensitive. 1-64 characters.
 * @param title          String Sticker set title, 1-64 characters
 * @param pngSticker     InputFile or String	Optional
 *                       image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px, and either width or height must be exactly 512px.
 *                       Pass a file_id as a String to send a file that already exists on the Telegram servers, pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data.
 *                       [[https://core.telegram.org/bots/api#sending-files More info on Sending Files]]
 * @param tgsSticker     InputFile Optional TGS animation with the sticker, uploaded using multipart/form-data. See
 *                       [[https://core.telegram.org/stickers#animated-sticker-requirements for technical requirements]]
 * @param webmSticker    InputFile Optional WEBM video with the sticker, uploaded using multipart/form-data see
 *                       [[https://core.telegram.org/stickers#video-sticker-requirements for technical requirements]]
 * @param stickerType    Optional StickerType. Type of stickers in the set, pass “regular” or “mask”. Custom emoji sticker sets can't be created via the Bot API at the moment. By default, a regular sticker set is created.
 * @param emojis	       String One or more emoji corresponding to the sticker
 * @param containsMasks  Boolean Optional Pass True, if a set of mask stickers should be created. Deprecated, use stickerType instead
 * @param maskPosition   MaskPosition Optional Position where the mask should be placed on faces
 */
case class CreateNewStickerSet(
  userId: Long,
  name: String,
  title: String,
  pngSticker: Option[InputFile] = None,
  tgsSticker: Option[InputFile] = None,
  webmSticker: Option[InputFile] = None,
  stickerType: Option[StickerType] = None,
  emojis: String,
  containsMasks: Option[Boolean] = None,
  maskPosition: Option[MaskPosition] = None
) extends MultipartRequest[Boolean] {

  require(
    Seq[Option[_]](
      pngSticker,
      tgsSticker,
      webmSticker
    ).count(_.isDefined) == 1,
    "You must use exactly one of the fields png_sticker, tgs_sticker, or webm_sticker"
  )

  override def getFiles: List[(String, InputFile)] = List(
    "png_sticker"  -> pngSticker,
    "tgs_sticker"  -> tgsSticker,
    "webm_sticker" -> webmSticker
  ).collect { case (key, Some(value)) => (key, value) }
}
