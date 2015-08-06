package info.mukel.telegram.bots.api

/**
  * PhotoSize
  *
  * This object represents one size of a photo or a file / sticker thumbnail.
  * Field 	Type 	Description
  * file_id 	String 	Unique identifier for this file
  * width 	Integer 	Photo width
  * height 	Integer 	Photo height
  * file_size 	Integer 	Optional. File size
  */
case class PhotoSize(
                       fileId   : String,
                       width    : Int,
                       height   : Int,
                       fileSize : Option[Int] = None
                       )
