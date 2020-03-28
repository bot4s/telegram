package com.bot4s.telegram.methods

import com.bot4s.telegram.models._

/** Use this method to edit audio, document, photo, or video messages.
  * If a message is a part of a message album, then it can be edited only to a photo or a video.
  * Otherwise, message type can be changed arbitrarily.
  * When inline message is edited, new file can't be uploaded.
  * Use previously uploaded file via its file_id or specify a URL.
  * On success, if the edited message was sent by the bot, the edited Message is returned, otherwise True is returned.
  *
  * @param chatId           Optional Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param messageId        Integer 	Optional Required if inline_message_id is not specified. Identifier of the sent message
  * @param inlineMessageId  String Optional 	Required if chat_id and message_id are not specified. Identifier of the inline message
  * @param media            InputMedia 	Yes 	A JSON-serialized object for a new media content of the message
  * @param replyMarkup      InlineKeyboardMarkup Optional A JSON-serialized object for a new inline keyboard.
  */
case class EditMessageMedia(chatId: Option[ChatId] = None,
                            messageId: Option[Int] = None,
                            inlineMessageId: Option[String] = None,
                            media: InputMedia,
                            replyMarkup: Option[InlineKeyboardMarkup] = None)
    extends MultipartRequest[Either[Boolean, Message]] {

  override def getFiles: List[(String, InputFile)] = {
    media.getFiles
  }
}
