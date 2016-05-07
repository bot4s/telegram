package info.mukel.telegram.bots.v2.methods

import info.mukel.telegram.bots.v2.model.User

/** getMe
  *
  * A simple method for testing your bot's auth token. Requires no parameters.
  * Returns basic information about the bot in form of a User object.
  */
case object GetMe extends ApiRequestJson[User]
