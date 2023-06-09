package com.bot4s.telegram.methods

import com.bot4s.telegram.models.BotName

/**
 * Use this method to get the current bot name for the given user language. Returns BotName on success.
 * @param languageCode A two-letter ISO 639-1 language code or an empty string
 */
case class GetMyName(
  languageCode: Option[String] = None
) extends JsonRequest[BotName]
