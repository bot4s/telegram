package info.mukel.telegram.bots.v2.model

/** Chat
  * This object represents a chat.
  *
  * @param id         Integer	Unique identifier for this chat, not exceeding 1e13 by absolute value
  * @param type       String	Type of chat, can be either “private”, “group”, “supergroup” or “channel”
  * @param title      String	Optional. Title, for channels and group chats
  * @param username   String	Optional. Username, for private chats and channels if available
  * @param firstName  String	Optional. First name of the other party in a private chat
  * @param lastName   String	Optional. Last name of the other party in a private chat
  */
case class Chat(
                 id        : Long,
                 `type`    : String,
                 title     : Option[String] = None,
                 username  : Option[String] = None,
                 firstName : Option[String] = None,
                 lastName  : Option[String] = None
               )
