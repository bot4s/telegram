package info.mukel.telegrambot4s.methods

/**
  * Use this method to move a sticker in a set created by the bot to a specific position.
  * Returns True on success.
  *
  * @param sticker   String File identifier of the sticker
  * @param position  Integer New sticker position in the set, zero-based
  */
case class SetStickerPositionInSet(
                                    sticker  : String,
                                    position : Int) extends ApiRequestJson[Boolean]
