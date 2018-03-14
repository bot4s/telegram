package info.mukel.telegrambot4s.models

import info.mukel.telegrambot4s.methods.ParseMode.ParseMode

sealed trait InputMedia

/**
  * Represents a photo to be sent.
  *
  * @param type    String Type of the result, must be photo
  * @param media   String File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended),
  *                pass an HTTP URL for Telegram to get a file from the Internet,
  *                or pass "attach://<file_attach_name>" to upload a new one using multipart/form-data under <file_attach_name> name.
  *                More info on Sending Files »
  * @param caption String Optional. Caption of the photo to be sent, 0-200 characters
  * @param parseMode String Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic,
  *                  fixed-width text or inline URLs in the media caption.
  */
case class InputMediaPhoto(media     : String,
                           caption   : Option[String] = None,
                           parseMode : Option[ParseMode] = None,
                           `type`    : String = "photo") extends InputMedia

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
  */
case class InputMediaVideo(media     : String,
                           caption   : Option[String] = None,
                           width     : Option[Int] = None,
                           height    : Option[Int] = None,
                           duration  : Option[Int] = None,
                           parseMode : Option[ParseMode] = None,
                           supportsStreaming : Option[ParseMode] = None,
                           `type`    : String = "video") extends InputMedia
