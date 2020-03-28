package com.bot4s.telegram.models

import com.bot4s.telegram.models.MessageEntityType.MessageEntityType

/** This object represents one special entity in a text message.
  *
  * For example, hashtags, usernames, URLs, etc.
  *
  * @param type    String Type of the entity.
  *                One of mention (@username), hashtag, bot_command, url, email, bold (bold text), italic (italic text),
  *                code (monowidth string), pre (monowidth block), text_link (for clickable text URLs),
  *                text_mention (for users without usernames)
  * @param offset  Integer Offset in UTF-16 code units to the start of the entity
  * @param length  Integer Length of the entity in UTF-16 code units
  * @param url     String Optional For "text_link" only, url that will be opened after user taps on the text
  * @param user    User Optional. For "text_mention" only, the mentioned user
  */
case class MessageEntity(`type`: MessageEntityType,
                         offset: Int,
                         length: Int,
                         url: Option[String] = None,
                         user: Option[User] = None)
