package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.extras.Configuration
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Contains information about a Web App.
 *
 * @param url String. An HTTPS URL of a Web App to be opened with additional data as specified in Initializing Web Apps
 */
case class WebAppInfo(
  url: String
)

object WebAppInfo {
  implicit val customConfig: Configuration       = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[WebAppInfo] = deriveDecoder[WebAppInfo]
  implicit val circeEncoder: Encoder[WebAppInfo] = deriveConfiguredEncoder[WebAppInfo]
}
