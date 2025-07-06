package com.bot4s.telegram.methods

import com.bot4s.telegram.models.BotDescription
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to get the current bot description for the given user language.
 * @param languageCode A two-letter ISO 639-1 language code or an empty string
 */
case class GetMyDescription(
  languageCode: Option[String] = None
) extends JsonRequest {
  type Response = BotDescription
}

object GetMyDescription {
  implicit val customConfig: Configuration                        = Configuration.default.withSnakeCaseMemberNames
  implicit val getMyDescriptionEncoder: Encoder[GetMyDescription] = deriveConfiguredEncoder[GetMyDescription]
}
