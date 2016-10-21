package info.mukel.telegrambot4s.models

/** This object represents an incoming callback query from a callback button in an inline keyboard.
  * If the button that originated the query was attached to a message sent by the bot, the field message will be presented.
  * If the button was attached to a message sent via the bot (in inline mode), the field inline_message_id will be presented.
  *
  * @param id               String Unique identifier for this query
  * @param from             User Sender
  * @param message          Message Optional Message with the callback button that originated the query. Note that message content and message date will not be available if the message is too old
  * @param inlineMessageId  String Optional Identifier of the message sent via the bot in inline mode, that originated the query
  * @param chatInstance     String	Identifier, uniquely corresponding to the chat to which the message with the callback button was sent. Useful for high scores in games.
  * @param data	String      Optional. Data associated with the callback button. Be aware that a bad client can send arbitrary data in this field.
  * @param gameShortName    String	Optional. Short name of a Game to be returned, serves as the unique identifier for the game
  */
case class CallbackQuery(
                        id              : String,
                        from            : User,
                        message         : Option[Message] = None,
                        inlineMessageId : Option[String] = None,
                        chatInstance    : String,
                        data            : Option[String] = None,
                        gameShortName   : Option[String] = None
                        )
