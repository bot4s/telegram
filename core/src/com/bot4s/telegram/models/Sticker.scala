package com.bot4s.telegram.models

/** This object represents a sticker.
  *
  * @param fileId        Identifier for this file
  * @param fileUniqueId  Unique identifier for this file
  * @param width         Sticker width
  * @param height        Sticker height
  * @param isAnimated    Boolean True, if the sticker is animated
  * @param thumb         Optional Sticker thumbnail in .webp or .jpg format
  * @param emoji         Optional. Emoji associated with the sticker
  * @param setName       String Optional. Name of the sticker set to which the sticker belongs
  * @param maskPosition	 MaskPosition	Optional.
  *                      For mask stickers, the position where the mask should be placed
  * @param fileSize      Integer Optional. File size
  */
case class Sticker(
                    fileId       : String,
                    fileUniqueId : String,
                    width        : Int,
                    height       : Int,
                    isAnimated   : Boolean,
                    thumb        : Option[PhotoSize] = None,
                    emoji        : Option[String] = None,
                    setName      : Option[String] = None,
                    maskPosition : Option[MaskPosition] = None,
                    fileSize     : Option[Int] = None
                  )
