package com.bot4s.telegram.methods

import com.bot4s.telegram.models.BotShortDescription

/**
 * Use this method to get the current bot short description for the given user language.
 * @param languageCode A two-letter ISO 639-1 language code or an empty string
 */
case class GetMyShortDescription(
  languageCode: Option[String] = None
) extends JsonRequest[BotShortDescription]
