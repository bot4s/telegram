package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.extras.Configuration

/**
 * This object represents a video message (available in Telegram apps as of v.4.0).
 *
 * @param fileId       String Identifier for this file
 * @param fileUniqueId String Unique identifier for this file
 * @param length       Integer Video width and height as defined by sender
 * @param duration     Integer Duration of the video in seconds as defined by sender
 * @param thumbnail    PhotoSize Optional. Video thumbnail
 * @param fileSize     Integer Optional. File size
 */
case class VideoNote(
  fileId: String,
  fileUniqueId: String,
  length: Int,
  duration: Int,
  thumbnail: Option[PhotoSize] = None,
  fileSize: Option[Int] = None
)

object VideoNote {
  implicit val customConfig: Configuration      = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[VideoNote] = deriveDecoder[VideoNote]
  implicit val circeEncoder: Encoder[VideoNote] = deriveConfiguredEncoder
}
