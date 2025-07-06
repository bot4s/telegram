package com.bot4s.telegram.methods

import io.circe.Encoder

/**
 * Use this method to remove webhook integration if you decide to switch back to getUpdates.
 * Returns True on success. Requires no parameters.
 */
case object DeleteWebhook extends JsonRequest {
  type Response = Boolean
  implicit val circeEncoder: Encoder[DeleteWebhook.type] = Encoder.instance(_ => io.circe.Json.Null)
}
