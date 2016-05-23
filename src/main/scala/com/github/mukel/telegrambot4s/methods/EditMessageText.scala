package com.github.mukel.telegrambot4s.methods

import com.github.mukel.telegrambot4s.methods.ParseMode.ParseMode
import com.github.mukel.telegrambot4s.models.{Message, ReplyMarkup}

/** editMessageText
  *
  * Use this method to edit text messages sent by the bot or via the bot (for inline bots).
  * On success, if edited message is sent by the bot, the edited Message is returned, otherwise True is returned.
  *
  * @param chatId                 Integer or String	Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param messageId              Integer	Required if inline_message_id is not specified. Unique identifier of the sent message
  * @param inlineMessageId        String	Required if chat_id and message_id are not specified. Identifier of the inline message
  * @param text                   String	New text of the message
  * @param parseMode              String	Optional	Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
  * @param disableWebPagePreview  Boolean	Optional	Disables link previews for links in this message
  * @param replyMarkup            InlineKeyboardMarkup	Optional	A JSON-serialized object for an inline keyboard.
  */
case class EditMessageText(
                          chatId                : Option[Either[Long, String]] = None,
                          messageId             : Option[Long] = None,
                          inlineMessageId       : Option[Long] = None,
                          text                  : String,
                          parseMode             : Option[ParseMode] = None,
                          disableWebPagePreview : Option[Boolean] = None,
                          replyMarkup           : Option[ReplyMarkup] = None
                          ) extends ApiRequestJson[Message]
