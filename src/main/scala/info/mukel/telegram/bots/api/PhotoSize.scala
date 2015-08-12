package info.mukel.telegram.bots.api

/**
 * PhotoSize
 *
 * This object represents one size of a photo or a file / sticker thumbnail.
 *
 * @param fileId    Unique identifier for this file
 * @param width 	  Photo width
 * @param height 	  Photo height
 * @param fileSize  Optional. File size
 */
case class PhotoSize(
                       fileId   : String,
                       width    : Int,
                       height   : Int,
                       fileSize : Option[Int] = None
                       )
