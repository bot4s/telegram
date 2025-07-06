package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.Configuration

/**
 * You can provide an animation for your game so that it looks stylish in chats (check out Lumberjack for an example).
 * This object represents an animation file to be displayed in the message containing a game.
 *
 * @param fileId       String File identifier
 * @param fileUniqueId String Unique file identifier
 * @param thumbnail   PhotoSize Optional. Animation thumbnail as defined by sender
 * @param fileName     String Optional. Original animation filename as defined by sender
 * @param mimeType     String Optional. MIME type of the file as defined by sender
 * @param fileSize     Integer Optional. File size
 */
case class Animation(
  fileId: String,
  fileUniqueId: String,
  thumbnail: Option[PhotoSize] = None,
  fileName: Option[String] = None,
  mimeType: Option[String] = None,
  fileSize: Option[Long] = None
)

object Animation {
  implicit val customConfig: Configuration      = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[Animation] = deriveDecoder[Animation]
  implicit val circeEncoder: Encoder[Animation] = deriveConfiguredEncoder[Animation]
}
