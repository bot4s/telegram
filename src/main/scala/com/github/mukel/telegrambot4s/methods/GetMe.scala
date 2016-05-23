package com.github.mukel.telegrambot4s.methods

import com.github.mukel.telegrambot4s.models.User

/** getMe
  *
  * A simple method for testing your bot's auth token. Requires no parameters.
  * Returns basic information about the bot in form of a User object.
  */
case object GetMe extends ApiRequestJson[User]
