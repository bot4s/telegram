package com.bot4s.telegram.methods

import com.bot4s.telegram.models.MaskPosition
import com.bot4s.telegram.models.InputFile

/**
 * Use this method to add a new sticker to a set created by the bot.
 * Returns True on success.
 *
 * @param userId        Long User identifier of sticker set owner
 * @param name          String	Sticker set name
 * @param pngSticker    InputFile or String Png image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px, and either width or height must be exactly 512px. Pass a file_id as a String to send a file that already exists on the Telegram servers, pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
 * @param tgsSticker    InputFile Optional TGS animation with the sticker, uploaded using multipart/form-data. See
 *                      [[https://core.telegram.org/stickers#animated-sticker-requirements for technical requirements]]
 * @param webmSticker   InputFile Optional WEBM video with the sticker, uploaded using multipart/form-data see
 *                      [[https://core.telegram.org/stickers#video-sticker-requirements for technical requirements]]
 * @param webmSticker   InputFile or String Png image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px, and either width or height must be exactly 512px. Pass a file_id as a String to send a file that already exists on the Telegram servers, pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
 * @param emojis        String One or more emoji corresponding to the sticker
 * @param maskPosition  MaskPosition Optional Position where the mask should be placed on faces
 */
case class AddStickerToSet(
  userId: Long,
  name: String,
  pngSticker: Option[InputFile],
  tgsSticker: Option[InputFile],
  webmSticker: Option[InputFile],
  emojis: String,
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
