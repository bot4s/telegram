package com.bot4s.telegram.methods

import com.bot4s.telegram.models.BotShortDescription
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to get the current bot short description for the given user language.
 * @param languageCode A two-letter ISO 639-1 language code or an empty string
 */
case class GetMyShortDescription(
  languageCode: Option[String] = None
) extends JsonRequest {
  type Response = BotShortDescription
}

object GetMyShortDescription {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val getMyShortDescriptionEncoder: Encoder[GetMyShortDescription] =
    deriveConfiguredEncoder[GetMyShortDescription]
}
