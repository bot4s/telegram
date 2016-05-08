package info.mukel.telegram.bots.v2.model

/**
  * Sticker
  *
  * This object represents a sticker.
  *
  * @param fileId    Unique identifier for this file
  * @param width     Sticker width
  * @param height    Sticker height
  * @param thumb 	   Optional Sticker thumbnail in .webp or .jpg format
  * @param emoji     Optional. Emoji associated with the sticker
  * @param fileSize  Optional File size
  */
case class Sticker(
                    fileId   : String,
                    width    : Int,
                    height   : Int,
                    thumb    : Option[PhotoSize] = None,
                    emoji    : Option[String] = None,
                    fileSize : Option[Int] = None
                  )
