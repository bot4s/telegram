package com.bot4s.telegram.models

/**
 * Contains data sent from a Web App to the bot.
 *
 * @param data String. Be aware that a bad client can send arbitrary data in this field.
 * @param buttonText String. Text of the web_app keyboard button, from which the Web App was opened. Be aware that a bad client can send arbitrary data in this field.
 */
case class WebAppData(
  data: String,
  buttonText: String
)
