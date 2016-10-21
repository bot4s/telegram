package info.mukel.telegrambot4s.methods

/** Use this method to send answers to callback queries sent from inline keyboards.
  *
  * The answer will be displayed to the user as a notification at the top of the chat screen or as an alert.
  * On success, True is returned.
  *
  * @param callbackQueryId String	Unique identifier for the query to be answered
  * @param text            String	Optional	Text of the notification. If not specified, nothing will be shown to the user
  * @param showAlert       Boolean	Optional	If true, an alert will be shown by the client instead of a notification at the top of the chat screen. Defaults to false.
  * @param url             String Optional	URL that will be opened by the user's client. If you have created a Game and accepted the conditions via @Botfather, specify the URL that opens your game – note that this will only work if the query comes from a callback_game button.
  *                        Otherwise, you may use links like telegram.me/your_bot?start=XXXX that open your bot with a parameter.
  */
case class AnswerCallbackQuery(
                                callbackQueryId : String,
                                text            : Option[String] = None,
                                showAlert       : Option[String] = None,
                                url             : Option[String] = None
                              ) extends ApiRequestJson[Boolean]
