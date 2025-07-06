package com.bot4s.telegram.methods

import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to change the bot's name. Returns True on success.
 *
 * @param name   	New bot name; 0-64 characters. Pass an empty string to remove the dedicated name for the given language.
 * @param languageCode A two-letter ISO 639-1 language code. If empty, the  description will be applied to all users for whose language there is no dedicated  description.
 */
case class SetMyName(
  name: Option[String] = None,
  languageCode: Option[String] = None
) extends JsonRequest {
  type Response = Boolean
}

object SetMyName {
  implicit val customConfig: Configuration          = Configuration.default.withSnakeCaseMemberNames
  implicit val setMyNameEncoder: Encoder[SetMyName] = deriveConfiguredEncoder[SetMyName]
}
