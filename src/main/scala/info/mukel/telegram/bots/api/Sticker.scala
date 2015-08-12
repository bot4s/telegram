package info.mukel.telegram.bots.api

/**
 * Sticker
 *
 * This object represents a sticker.
 *
 * @param fileId    Unique identifier for this file
 * @param width     Sticker width
 * @param height    Sticker height
 * @param thumb 	   Optional. Sticker thumbnail in .webp or .jpg format
 * @param fileSize  Optional. File size
 */
case class Sticker(
                     fileId   : String,
                     width    : Int,
                     height   : Int,
                     thumb    : Option[PhotoSize] = None,
                     fileSize : Option[Int] = None
                     )
