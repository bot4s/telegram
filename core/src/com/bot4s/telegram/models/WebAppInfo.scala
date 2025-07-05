package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * Contains information about a Web App.
 *
 * @param url String. An HTTPS URL of a Web App to be opened with additional data as specified in Initializing Web Apps
 */
case class WebAppInfo(
  url: String
)

object WebAppInfo {
  implicit val circeDecoder: Decoder[WebAppInfo] = deriveDecoder[WebAppInfo]
}
