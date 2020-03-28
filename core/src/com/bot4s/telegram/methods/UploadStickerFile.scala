package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{File, InputFile}

/**
  * Use this method to upload a .png file with a sticker for later use in createNewStickerSet
  * and addStickerToSet methods (can be used multiple times).
  * Returns the uploaded File on success.
  *
  * @param userId      Integer User identifier of sticker file owner
  * @param pngSticker  InputFile Png image with the sticker, must be up to 512 kilobytes in size,
  *                    dimensions must not exceed 512px, and either width or height must be exactly 512px.
  *                    [[https://core.telegram.org/bots/api#sending-files More info on Sending Files]]
  */
case class UploadStickerFile(userId: Int, pngSticker: InputFile)
    extends MultipartRequest[File] {
  override def getFiles: List[(String, InputFile)] =
    List("png_sticker" -> pngSticker)
}
