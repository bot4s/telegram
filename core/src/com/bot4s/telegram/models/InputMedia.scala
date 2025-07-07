package com.bot4s.telegram.models

import com.bot4s.telegram.methods.ParseMode.ParseMode
import io.circe.Encoder
import io.circe.syntax.EncoderOps
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.extras.Configuration

sealed trait InputMedia {
  def getFiles: List[(String, InputFile)] = {
    val attachPrefix = "attach://"
    val t = this match {
      case photo: InputMediaPhoto         => photo.photo.map(photo.media.stripPrefix(attachPrefix) -> _)
      case video: InputMediaVideo         => video.video.map(video.media.stripPrefix(attachPrefix) -> _)
      case audio: InputMediaAudio         => audio.audio.map(audio.media.stripPrefix(attachPrefix) -> _)
      case document: InputMediaDocument   => document.document.map(document.media.stripPrefix(attachPrefix) -> _)
      case animation: InputMediaAnimation => animation.animation.map(animation.media.stripPrefix(attachPrefix) -> _)
    }
    t.toList
  }
}

/**
 * Represents a photo to be sent.
 *
 * @param type    String Type of the result, must be photo
 * @param media   String File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended),
 *                pass an HTTP URL for Telegram to get a file from the Internet,
 *                or pass "attach://<file_attach_name>" to upload a new one using multipart/form-data under <file_attach_name> name.
 *                More info on Sending Files »
 * @param photo   InputFile for the "attach://file_attach_name" case.
 *
 * @param caption String Optional. Caption of the photo to be sent, 0-200 characters
 * @param parseMode String Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic,
 *                  fixed-width text or inline URLs in the media caption.
 * @param hasSpoiler Optional. Pass True if the photo needs to be covered with a spoiler animation
 */
case class InputMediaPhoto(
  media: String,
  photo: Option[InputFile], // file to attach
  caption: Option[String] = None,
  parseMode: Option[ParseMode] = None,
  hasSpoiler: Option[Boolean] = None,
  `type`: String = "photo"
) extends InputMedia

/**
 * Represents a video to be sent.
 * @param type      String Type of the result, must be video
 * @param media     String File to send.
 *                  Pass a file_id to send a file that exists on the Telegram servers (recommended),
 *                  pass an HTTP URL for Telegram to get a file from the Internet,
 *                  or pass "attach://<file_attach_name>" to upload a new one using multipart/form-data under <file_attach_name> name.
 *                  More info on Sending Files »
 * @param caption   String Optional. Caption of the video to be sent, 0-200 characters
 * @param width     Integer Optional. Video width
 * @param height    Integer Optional. Video height
 * @param duration  Integer Optional. Video duration
 * @param parseMode String Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic,
 *                  fixed-width text or inline URLs in the media caption.
 * @param supportsStreaming Boolean Optional. Pass True, if the uploaded video is suitable for streaming
 * @param hasSpoiler Boolean Optional. Pass True if the video needs to be covered with a spoiler animation
 */
case class InputMediaVideo(
  media: String,
  video: Option[InputFile] = None, // file to attach
  caption: Option[String] = None,
  width: Option[Int] = None,
  height: Option[Int] = None,
  duration: Option[Int] = None,
  parseMode: Option[ParseMode] = None,
  supportsStreaming: Option[Boolean] = None,
  hasSpoiler: Option[Boolean] = None,
  `type`: String = "video"
) extends InputMedia

/**
 * Represents an animation file (GIF or H.264/MPEG-4 AVC video without sound) to be sent.
 * @param type      String Type of the result, must be animation
 * @param media     String File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended),
 *                  pass an HTTP URL for Telegram to get a file from the Internet,
 *                  or pass “attach://<file_attach_name>” to upload a new one using multipart/form-data under <file_attach_name> name.
 *                  More info on Sending Files »
 * @param thumbnail InputFile or String 	Optional. Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail‘s width and height should not exceed 90. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param caption   String Optional. Caption of the animation to be sent, 0-200 characters
 * @param parseMode String Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.
 * @param width     Integer Optional. Animation width
 * @param height    Integer Optional. Animation height
 * @param duration  Integer Optional. Animation duration
 * @param hasSpoiler Boolean Optional. Pass True if the animation needs to be covered with a spoiler animation
 */
case class InputMediaAnimation(
  media: String,
  animation: Option[InputFile] = None, // file to attach
  thumbnail: Option[InputFile] = None,
  caption: Option[String] = None,
  parseMode: Option[ParseMode] = None,
  width: Option[Int] = None,
  height: Option[Int] = None,
  duration: Option[Int] = None,
  hasSpoiler: Option[Boolean] = None,
  `type`: String = "animation"
) extends InputMedia

/**
 * Represents an audio file to be treated as music to be sent.
 * @param type      String Type of the result, must be audio
 * @param media     String File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended),
 *                  pass an HTTP URL for Telegram to get a file from the Internet,
 *                  pass “attach://<file_attach_name>” to upload a new one using multipart/form-data under <file_attach_name> name.
 *                  More info on Sending Files »
 * @param thumbnail InputFile or String 	Optional. Thumbnail of the file sent.
 *                  thumbnail should be in JPEG format and less than 200 kB in size.
 *                  A thumbnail‘s width and height should not exceed 90.
 *                  if the file is not uploaded using multipart/form-data.
 *                  Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param caption   String Optional. Caption of the audio to be sent, 0-200 characters
 * @param parseMode String Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.
 * @param duration  Integer Optional. Duration of the audio in seconds
 * @param performer String Optional. Performer of the audio
 * @param title     String Optional. Title of the audio
 */
case class InputMediaAudio(
  media: String,
  audio: Option[InputFile] = None, // file to attach
  thumbnail: Option[InputFile] = None,
  caption: Option[String] = None,
  parseMode: Option[ParseMode] = None,
  duration: Option[Int] = None,
  performer: Option[String] = None,
  title: Option[String] = None,
  `type`: String = "audio"
) extends InputMedia

/**
 * Represents a general file to be sent.
 * @param type       String Type of the result, must be document
 * @param media      String File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended),
 *                   pass an HTTP URL for Telegram to get a file from the Internet,
 *                   or pass “attach://<file_attach_name>” to upload a new one using multipart/form-data under <file_attach_name> name.
 *                   More info on Sending Files »
 * @param thumbnail  InputFile or String Optional. Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail‘s width and height should not exceed 90. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
 * @param caption    String Optional. Caption of the document to be sent, 0-200 characters
 * @param parse_mode String 	Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.
 */
case class InputMediaDocument(
  media: String,
  document: Option[InputFile] = None, // file to attach
  thumbnail: Option[InputFile] = None,
  caption: Option[String] = None,
  parseMode: Option[ParseMode] = None,
  `type`: String = "document"
) extends InputMedia

object InputMediaDocument {
  implicit val customConfig: Configuration               = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[InputMediaDocument] = deriveConfiguredEncoder
}

object InputMediaAudio {
  implicit val customConfig: Configuration            = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[InputMediaAudio] = deriveConfiguredEncoder
}

object InputMediaAnimation {
  implicit val customConfig: Configuration                = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[InputMediaAnimation] = deriveConfiguredEncoder
}

object InputMediaVideo {
  implicit val customConfig: Configuration            = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[InputMediaVideo] = deriveConfiguredEncoder
}

object InputMediaPhoto {
  implicit val customConfig: Configuration            = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[InputMediaPhoto] = deriveConfiguredEncoder
}

object InputMedia {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val inputMessageContentEncoder: Encoder[InputMedia] = Encoder.instance {
    case q: InputMediaPhoto     => q.asJson
    case q: InputMediaVideo     => q.asJson
    case q: InputMediaAnimation => q.asJson
    case q: InputMediaAudio     => q.asJson
    case q: InputMediaDocument  => q.asJson
  }
}
