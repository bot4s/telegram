package com.bot4s.telegram.models

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.Configuration

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

object PhotoSize {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[PhotoSize] = deriveDecoder[PhotoSize]
  implicit val circeEncoder: Encoder[PhotoSize] = deriveConfiguredEncoder[PhotoSize]
}
