package com.bot4s.telegram.methods

import com.bot4s.telegram.models.BotName
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to get the current bot name for the given user language. Returns BotName on success.
 * @param languageCode A two-letter ISO 639-1 language code or an empty string
 */
case class GetMyName(
  languageCode: Option[String] = None
) extends JsonRequest[BotName]

object GetMyName {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val getMyNameEncoder: Encoder[GetMyName] = deriveConfiguredEncoder[GetMyName]
}
