package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.extras.Configuration

/**
 * This object represents a video file.
 *
 * @param fileId       Identifier for this file
 * @param fileUniqueId Unique file identifier
 * @param width        Video width as defined by sender
 * @param height       Video height as defined by sender
 * @param duration     Duration of the video in seconds as defined by sender
 * @param thumbnail    Optional Video thumbnail
 * @param mimeType     Optional Mime type of a file as defined by sender
 * @param fileSize     Optional File size
 */
case class Video(
  fileId: String,
  fileUniqueId: String,
  width: Int,
  height: Int,
  duration: Int,
  thumbnail: Option[PhotoSize] = None,
  mimeType: Option[String] = None,
  fileSize: Option[Long] = None
)

object Video {
  implicit val customConfig: Configuration  = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[Video] = deriveDecoder[Video]
  implicit val circeEncoder: Encoder[Video] = deriveConfiguredEncoder[Video]
}
