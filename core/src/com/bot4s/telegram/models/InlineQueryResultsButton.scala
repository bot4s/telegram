package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents a button to be shown above inline query results. You must use exactly one of the optional fields.
 *
 * @param text             Label text on the button
 * @param webApp           Description of the Web App that will be launched when the user presses the button. The Web App will be able to switch back to the inline mode using the method switchInlineQuery inside the Web App.
 * @param startParameter   Deep-linking parameter for the /start message sent to the bot when a user presses the button. 1-64 characters, only A-Z, a-z, 0-9, _ and - are allowed.
 */
final case class InlineQueryResultsButton(
  text: String,
  webApp: Option[WebAppInfo] = None,
  startParameter: Option[String] = None
)

object InlineQueryResultsButton {
  implicit val circeDecoder: Decoder[InlineQueryResultsButton] = deriveDecoder[InlineQueryResultsButton]
}
