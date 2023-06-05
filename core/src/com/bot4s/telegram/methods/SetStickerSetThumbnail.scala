package com.bot4s.telegram.methods

import com.bot4s.telegram.models.InputFile

/**
 * Use this method to set the thumbnail of a regular or mask sticker set.
 * The format of the thumbnail file must match the format of the stickers in the set. Returns True on success.
 *
 * @param name      Sticker set name
 * @param userId    User identifier of the sticker set owner
 * @param thumbnail A .WEBP or .PNG image with the thumbnail, must be up to 128 kilobytes in size and have a width and height of exactly 100px
 *                  A .TGS animation with a thumbnail up to 32 kilobytes in size (see https://core.telegram.org/stickers#animated-sticker-requirements for animated sticker technical requirements)
 *                  A WEBM video with the thumbnail up to 32 kilobytes in size; see https://core.telegram.org/stickers#video-sticker-requirements for video sticker technical requirements.
 *                  Pass a file_id as a String to send a file that already exists on the Telegram servers, pass an HTTP URL as a String for Telegram to get a file from the Internet
 *                  Upload a new one using multipart/form-data. More information on Sending Files ». Animated and video sticker set thumbnails can't be uploaded via HTTP URL.
 *                  If omitted, then the thumbnail is dropped and the first sticker is used as the thumbnail.
 */
case class SetStickerSetThumbnail(
  name: String,
  userId: Int,
  thumbnail: Option[InputFile]
) extends JsonRequest[Boolean]
