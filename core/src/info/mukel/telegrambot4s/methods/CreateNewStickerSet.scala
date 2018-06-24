package info.mukel.telegrambot4s.methods

import info.mukel.telegrambot4s.models.{InputFile, MaskPosition}

/**
  * Use this method to create new sticker set owned by a user.
  * The bot will be able to edit the created sticker set.
  * Returns True on success.
  *
  * @param userId         Integer User identifier of created sticker set owner
  * @param name           String Short name of sticker set, to be used in t.me/addstickers/ URLs (e.g., animals). Can contain only english letters, digits and underscores. Must begin with a letter, can't contain consecutive underscores and must end in “_by_<bot username>”. <bot_username> is case insensitive. 1-64 characters.
  * @param title          String Sticker set title, 1-64 characters
  * @param pngSticker     InputFile or String	Png image with the sticker, must be up to 512 kilobytes in size,
  *                       dimensions must not exceed 512px, and either width or height must be exactly 512px.
  *                       Pass a file_id as a String to send a file that already exists on the Telegram servers,
  *                       pass an HTTP URL as a String for Telegram to get a file from the Internet,
  *                       or upload a new one using multipart/form-data.
  *                       [[https://core.telegram.org/bots/api#sending-files More info on Sending Files]]
  * @param emojis	        String One or more emoji corresponding to the sticker
  * @param containsMasks  Boolean Optional Pass True, if a set of mask stickers should be created
  * @param maskPosition   MaskPosition Optional Position where the mask should be placed on faces
  */
case class CreateNewStickerSet(
                              userId        : Int,
                              name          : String,
                              title         : String,
                              pngSticker    : InputFile,
                              emojis        : String,
                              containsMasks : Option[Boolean] = None,
                              maskPosition  : Option[MaskPosition] = None
                              ) extends ApiRequestMultipart[Boolean] {
  override def getFiles: List[(String, InputFile)] = List("png_sticker" -> pngSticker)
}
