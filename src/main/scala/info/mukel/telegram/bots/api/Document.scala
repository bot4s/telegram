package info.mukel.telegram.bots.api

/**
  * Document
  *
  * This object represents a general file (as opposed to photos and audio files).
  * Field 	Type 	Description
  * file_id 	String 	Unique file identifier
  * thumb 	PhotoSize 	Optional. Document thumbnail as defined by sender
  * file_name 	String 	Optional. Original filename as defined by sender
  * mime_type 	String 	Optional. MIME type of the file as defined by sender
  * file_size 	Integer 	Optional. File size
  */
case class Document(
                      fileId   : String,
                      thumb    : Option[PhotoSize] = None,
                      fileName : Option[String] = None,
                      mimeType : Option[String] = None,
                      fileSize : Option[Int] = None
                      )
