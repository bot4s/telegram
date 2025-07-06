package com.bot4s.telegram.models

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.Configuration

/**
 * This object represents a general file (as opposed to photos and audio files).
 *
 * @param fileId       File identifier
 * @param fileUniqueId Unique file identifier
 * @param thumbnail    Optional Document thumbnail as defined by sender
 * @param fileName     Optional Original filename as defined by sender
 * @param mimeType     Optional MIME type of the file as defined by sender
 * @param fileSize     Optional File size
 */
case class Document(
  fileId: String,
  fileUniqueId: String,
  thumbnail: Option[PhotoSize] = None,
  fileName: Option[String] = None,
  mimeType: Option[String] = None,
  fileSize: Option[Long] = None
)

object Document {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[Document] = deriveDecoder[Document]
  implicit val circeEncoder: Encoder[Document] = deriveConfiguredEncoder[Document]
}
