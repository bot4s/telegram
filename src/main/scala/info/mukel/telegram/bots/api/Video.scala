package info.mukel.telegram.bots.api

/**
  * Video
  *
  * This object represents a video file.
  * Field 	Type 	Description
  * file_id 	String 	Unique identifier for this file
  * width 	Integer 	Video width as defined by sender
  * height 	Integer 	Video height as defined by sender
  * duration 	Integer 	Duration of the video in seconds as defined by sender
  * thumb 	PhotoSize 	Optional. Video thumbnail
  * mime_type 	String 	Optional. Mime type of a file as defined by sender
  * file_size 	Integer 	Optional. File size
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
