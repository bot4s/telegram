package com.bot4s.telegram.methods

/**
 * Use this method to change the bot's description, which is shown on the bot's profile page and is sent together with the link when users share the bot.
 *
 * @param description  New bot description; 0-512 characters. Pass an empty string to remove the dedicated description for the given language
 * @param languageCode A two-letter ISO 639-1 language code. If empty, the  description will be applied to all users for whose language there is no dedicated  description.
 */
case class SetMyDescription(
  description: Option[String] = None,
  languageCode: Option[String] = None
) extends JsonRequest[Boolean]
