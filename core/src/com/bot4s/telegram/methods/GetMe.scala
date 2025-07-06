package com.bot4s.telegram.methods

import com.bot4s.telegram.models.User
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * A simple method for testing your bot's auth token. Requires no parameters.
 * Returns basic information about the bot in form of a User object.
 */
case object GetMe extends JsonRequest {
  type Response = User
  implicit val circeEncoder: Encoder[GetMe.type] = Encoder.instance(_ => io.circe.Json.Null)
}
