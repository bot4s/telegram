package info.mukel.telegram.bots.api

/**
 * Message
 *
 * This object represents a message.

 * @param messageId            Unique message identifier
 * @param from                 Sender
 * @param date 	               Date the message was sent in Unix time
 * @param chat 	               User or GroupChat 	Conversation the message belongs to — user in case of a private message, GroupChat in case of a group
 * @param forwardFrom          Optional. For forwarded messages, sender of the original message
 * @param forwardDate          Optional. For forwarded messages, date the original message was sent in Unix time
 * @param replyToMessage       Optional. For replies, the original message. Note that the Message object in this field will not contain further replyToMessage fields even if it itself is a reply.
 * @param text                 Optional. For text messages, the actual UTF-8 text of the message
 * @param audio                Optional. Message is an audio file, information about the file
 * @param document             Optional. Message is a general file, information about the file
 * @param photo                Optional. Message is a photo, available sizes of the photo
 * @param sticker              Optional. Message is a sticker, information about the sticker
 * @param video                Optional. Message is a video, information about the video
 * @param caption              Optional. Caption for the photo or video
 * @param contact 	           Optional. Message is a shared contact, information about the contact
 * @param location             Optional. Message is a shared location, information about the location
 * @param newChatParticipant 	 Optional. A new member was added to the group, information about them (this member may be bot itself)
 * @param leftChatParticipant  Optional. A member was removed from the group, information about them (this member may be bot itself)
 * @param newChatTitle         Optional. A group title was changed to this value
 * @param newChatPhoto         Optional. A group photo was change to this value
 * @param deleteChatPhoto      Optional. Informs that the group photo was deleted
 * @param groupChatCreated     Optional. Informs that the group has been created
 */
case class Message(
                     messageId           : Int,
                     from                : User,
                     date                : Int,
                     // TODO: This is a workaround to handle the limitations of the JSON deserialization.
                     // The correct type would be Either[User, GroupChat]
                     chat                :	UserOrGroupChat,

                     forwardFrom         :	Option[User] = None,
                     forwardDate         : Option[Int] = None,
                     replyToMessage      : Option[Message] = None,
                     text                : Option[String] = None,
                     audio               : Option[Audio] = None,
                     document            : Option[Document] = None,
                     photo               : Option[Array[PhotoSize]] = None,
                     sticker             : Option[Sticker] = None,
                     video               : Option[Video] = None,
                     caption             : Option[String] = None,
                     contact             : Option[Contact] = None,
                     location            : Option[Location] = None,
                     newChatParticipant  : Option[User] = None,
                     leftChatParticipant : Option[User] = None,
                     newChatTitle        : Option[String] = None,
                     newChatPhoto        : Option[Array[PhotoSize]] = None,
                     deleteChatPhoto     : Option[Boolean] = None,
                     groupChatCreated    : Option[Boolean] = None
                     )
