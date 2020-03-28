package com.bot4s.telegram.models

/** This object represents a Telegram user or bot.
  *
  * @param id           Unique identifier for this user or bot
  * @param isBot        Boolean True, if this user is a bot
  * @param firstName    User's or bot's first name
  * @param lastName     Optional User's or bot's last name
  * @param username     Optional User's or bot's username
  * @param languageCode String Optional. IETF language tag of the user's language
  */
case class User(id: Int,
                isBot: Boolean,
                firstName: String,
                lastName: Option[String] = None,
                username: Option[String] = None,
                languageCode: Option[String] = None)
