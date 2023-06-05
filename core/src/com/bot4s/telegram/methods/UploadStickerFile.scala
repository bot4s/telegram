package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ File, InputFile }

/**
 * Use this method to upload a file with a sticker for later use in the createNewStickerSet and addStickerToSet methods (the file can be used multiple times)
 * Returns the uploaded File on success.
 *
 * @param userId        User identifier of sticker file owner
 * @param sticker       A file with the sticker in .WEBP, .PNG, .TGS, or .WEBM format. See https://core.telegram.org/stickers for technical requirements. More information on Sending Files »
 * @param stickerFormat Format of the sticker, must be one of “static”, “animated”, “video”
 */
case class UploadStickerFile(userId: Long, sticker: InputFile, stickerFormat: String) extends MultipartRequest[File] {
  override def getFiles: List[(String, InputFile)] = List("sticker" -> sticker)
}
