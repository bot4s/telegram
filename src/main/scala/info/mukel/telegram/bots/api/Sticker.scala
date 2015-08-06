package info.mukel.telegram.bots.api

/**
  * Sticker
  *
  * This object represents a sticker.
  * Field 	Type 	Description
  * file_id 	String 	Unique identifier for this file
  * width 	Integer 	Sticker width
  * height 	Integer 	Sticker height
  * thumb 	PhotoSize 	Optional. Sticker thumbnail in .webp or .jpg format
  * file_size 	Integer 	Optional. File size
  */
case class Sticker(
                     fileId   : String,
                     width    : Int,
                     height   : Int,
                     thumb    : Option[PhotoSize] = None,
                     fileSize : Option[Int] = None
                     )
