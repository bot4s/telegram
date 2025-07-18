package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.Configuration

/**
 * This object represents an audio file (voice note).
 *
 * @param fileId       Identifier for this file
 * @param fileUniqueId Unique identifier for this file
 * @param duration     Duration of the audio in seconds as defined by sender
 * @param performer    Optional Performer of the audio as defined by sender or by audio tags
 * @param title        Optional Title of the audio as defined by sender or by audio tags
 * @param mimeType     Optional MIME type of the file as defined by sender
 * @param fileSize     Optional File size
 * @param thumbnail    PhotoSize Optional. Thumbnail of the album cover to which the music file belongs
 */
case class Audio(
  fileId: String,
  fileUniqueId: String,
  duration: Int,
  performer: Option[String] = None,
  title: Option[String] = None,
  mimeType: Option[String] = None,
  fileSize: Option[Long] = None,
  thumbnail: Option[PhotoSize]
)

object Audio {
  implicit val customConfig: Configuration  = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[Audio] = deriveDecoder[Audio]
  implicit val circeEncoder: Encoder[Audio] = deriveConfiguredEncoder[Audio]
}
