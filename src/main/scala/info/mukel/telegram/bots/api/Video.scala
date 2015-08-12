package info.mukel.telegram.bots.api

/**
 * Video
 *
 * This object represents a video file.
 *
 * @param fileId    Unique identifier for this file
 * @param width     Video width as defined by sender
 * @param height    Video height as defined by sender
 * @param duration  Duration of the video in seconds as defined by sender
 * @param thumb     Optional. Video thumbnail
 * @param mimeType  Optional. Mime type of a file as defined by sender
 * @param fileSize  Optional. File size
 */
case class Video(
                   fileId   : String,
                   width    : Int,
                   height   : Int,
                   duration : Int,
                   thumb    : Option[PhotoSize] = None,
                   mimeType : Option[String] = None,
                   fileSize : Option[Int] = None
                   )
