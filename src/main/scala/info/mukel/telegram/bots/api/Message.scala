package info.mukel.telegram.bots.api

/**
 * Message
 *
 * This object represents a message.
 *
 * @param messageId              Unique message identifier
 * @param from                   Sender
 * @param date 	                 Date the message was sent in Unix time
 * @param chat 	                 User or GroupChat 	Conversation the message belongs to — user in case of a private message, GroupChat in case of a group
 * @param forwardFrom            Optional. For forwarded messages, sender of the original message
 * @param forwardDate            Optional. For forwarded messages, date the original message was sent in Unix time
 * @param replyToMessage         Optional. For replies, the original message. Note that the Message object in this field will not contain further replyToMessage fields even if it itself is a reply.
 * @param text                   Optional. For text messages, the actual UTF-8 text of the message
 * @param audio                  Optional. Message is an audio file, information about the file
 * @param document               Optional. Message is a general file, information about the file
 * @param photo                  Optional. Message is a photo, available sizes of the photo
 * @param sticker                Optional. Message is a sticker, information about the sticker
 * @param video                  Optional. Message is a video, information about the video
 * @param voice                  Optional. Message is a voice message, information about the file
 * @param caption                Optional. Caption for the photo or video
 * @param contact 	             Optional. Message is a shared contact, information about the contact
 * @param location               Optional. Message is a shared location, information about the location
 * @param newChatParticipant     Optional. A new member was added to the group, information about them (this member may be bot itself)
 * @param leftChatParticipant    Optional. A member was removed from the group, information about them (this member may be bot itself)
 * @param newChatTitle           Optional. A group title was changed to this value
 * @param newChatPhoto           Optional. A group photo was change to this value
 * @param deleteChatPhoto        Optional. Informs that the group photo was deleted
 * @param groupChatCreated       Optional. Informs that the group has been created
 * @param supergroupChatCreated	 True	Optional. Service message: the supergroup has been created
 * @param channelChatCreated     True	Optional. Service message: the channel has been created
 * @param migrateToChatId        Integer	Optional. The group has been migrated to a supergroup with the specified identifier, not exceeding 1e13 by absolute value
 * @param migrateFromChatId      Integer	Optional. The supergroup has been migrated from a group with the specified identifier, not exceeding 1e13 by absolute value
 */
class Message(
              val messageId             : Int,
              val from                  : User,
              val date                  : Int,
              val chat                  : Chat,
              val forwardFrom           : Option[User] = None,
              val forwardDate           : Option[Int] = None,
              val replyToMessage        : Option[Message] = None,
              val text                  : Option[String] = None,
              val audio                 : Option[Audio] = None,
              val document              : Option[Document] = None,
              val photo                 : Option[Array[PhotoSize]] = None,
              val sticker               : Option[Sticker] = None,
              val video                 : Option[Video] = None,
              val voice                 : Option[Voice] = None,
              val caption               : Option[String] = None,
              val contact               : Option[Contact] = None,
              val location              : Option[Location] = None,
              val newChatParticipant    : Option[User] = None,
              val leftChatParticipant   : Option[User] = None,
              val newChatTitle          : Option[String] = None,
              val newChatPhoto          : Option[Array[PhotoSize]] = None,
              val deleteChatPhoto       : Option[Boolean] = None,
              val groupChatCreated      : Option[Boolean] = None,
              val supergroupChatCreated	: Option[Boolean] = None,
              val channelChatCreated	  : Option[Boolean] = None,
              val migrateToChatId	      : Option[Int] = None,
              val migrateFromChatId	    : Option[Int] = None
              )
