package com.bot4s.telegram.models

/**
 * This object represents one size of a photo or a file / sticker thumbnail.
 *
 * @param fileId       Unique identifier for this file, which can be used to download or reuse the file
 * @param fileUniqueId Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used to download or reuse the file.
 * @param width        Photo width
 * @param height       Photo height
 * @param fileSize     Optional File size
 */
case class PhotoSize(
  fileId: String,
  fileUniqueId: String,
  width: Int,
  height: Int,
  fileSize: Option[Int] = None
)
