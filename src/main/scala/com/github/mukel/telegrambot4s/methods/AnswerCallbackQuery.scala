package com.github.mukel.telegrambot4s.methods

/** Use this method to send answers to callback queries sent from inline keyboards.
  *
  * The answer will be displayed to the user as a notification at the top of the chat screen or as an alert.
  * On success, True is returned.
  *
  * @param callbackQueryId String	Unique identifier for the query to be answered
  * @param text            String	Optional	Text of the notification. If not specified, nothing will be shown to the user
  * @param showAlert       Boolean	Optional	If true, an alert will be shown by the client instead of a notification at the top of the chat screen. Defaults to false.
  */
case class AnswerCallbackQuery(
                                callbackQueryId : String,
                                text            : Option[String] = None,
                                showAlert       : Option[String] = None
                              ) extends ApiRequestJson[Boolean]
