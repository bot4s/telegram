package info.mukel.telegrambot4s.methods

import info.mukel.telegrambot4s.models.User

/** A simple method for testing your bot's auth token. Requires no parameters.
  * Returns basic information about the bot in form of a User object.
  */
case object GetMe extends ApiRequestJson[User]
