package info.mukel.telegram.bots.api

/**
  * Audio
  *
  * This object represents an audio file (voice note).
  * Field 	Type 	Description
  * file_id 	String 	Unique identifier for this file
  * duration 	Integer 	Duration of the audio in seconds as defined by sender
  * mime_type 	String 	Optional. MIME type of the file as defined by sender
  * file_size 	Integer 	Optional. File size
  */
case class Audio(
                   fileId   : String,
                   duration : Int,
                   mimeType : Option[String] = None,
                   fileSize : Option[Int] = None
                   )
