package info.mukel.telegram.bots.api

/**
 * Document
 *
 * This object represents a general file (as opposed to photos and audio files).
 *
 * @param fileId 	  Unique file identifier
 * @param thumb 	  Optional. Document thumbnail as defined by sender
 * @param fileName  Optional. Original filename as defined by sender
 * @param mimeType  Optional. MIME type of the file as defined by sender
 * @param fileSize  Optional. File size
 */
case class Document(
                      fileId   : String,
                      thumb    : Option[PhotoSize] = None,
                      fileName : Option[String] = None,
                      mimeType : Option[String] = None,
                      fileSize : Option[Int] = None
                      )
