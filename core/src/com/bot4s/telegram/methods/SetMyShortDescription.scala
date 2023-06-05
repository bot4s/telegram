package com.bot4s.telegram.methods

/**
 * Use this method to change the bot's short description, which is shown on the bot's profile page and is sent together with the link when users share the bot.
 *
 * @param shortDescription new short description for the bot; 0-120 characters. Pass an empty string to remove the dedicated short description for the given language.
 * @param languageCode A two-letter ISO 639-1 language code. If empty, the short description will be applied to all users for whose language there is no dedicated short description.
 */
case class SetMyShortDescription(
  shortDescription: Option[String] = None,
  languageCode: Option[String] = None
) extends JsonRequest[Boolean]
