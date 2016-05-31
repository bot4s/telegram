package com.github.mukel.telegrambot4s.models

/** This object represents a message.
  *
  * @param messageId              Unique message identifier
  * @param from                   Sender
  * @param date 	                Date the message was sent in Unix time
  * @param chat 	                User or GroupChat 	Conversation the message belongs to — user in case of a private message, GroupChat in case of a group
  * @param forwardFrom            Optional For forwarded messages, sender of the original message
  * @param forwardFromChat        Optional For messages forwarded from a channel, information about the original channel
  * @param forwardDate            Optional For forwarded messages, date the original message was sent in Unix time
  * @param replyToMessage         Optional For replies, the original message. Note that the Message object in this field will not contain further replyToMessage fields even if it itself is a reply.
  * @param editDate               Optional. Date the message was last edited in Unix time
  * @param text                   Optional For text messages, the actual UTF-8 text of the message
  * @param entities               Array of MessageEntity	Optional For text messages, special entities like usernames, URLs, bot commands, etc. that appear in the text
  * @param audio                  Optional Message is an audio file, information about the file
  * @param document               Optional Message is a general file, information about the file
  * @param photo                  Optional Message is a photo, available sizes of the photo
  * @param sticker                Optional Message is a sticker, information about the sticker
  * @param video                  Optional Message is a video, information about the video
  * @param voice                  Optional Message is a voice message, information about the file
  * @param caption                Optional Caption for the photo or video
  * @param contact 	              Optional Message is a shared contact, information about the contact
  * @param location               Optional Message is a shared location, information about the location
  * @param venue                  Venue	Optional Message is a venue, information about the venue
  * @param newChatMember          Optional A new member was added to the group, information about them (this member may be bot itself)
  * @param leftChatMember         Optional A member was removed from the group, information about them (this member may be bot itself)
  * @param newChatTitle           Optional A group title was changed to this value
  * @param newChatPhoto           Optional A group photo was change to this value
  * @param deleteChatPhoto        Optional Informs that the group photo was deleted
  * @param groupChatCreated       Optional Informs that the group has been created
  * @param supergroupChatCreated	True	Optional Service message: the supergroup has been created
  * @param channelChatCreated     True	Optional Service message: the channel has been created
  * @param migrateToChatId        Integer	Optional The group has been migrated to a supergroup with the specified identifier, not exceeding 1e13 by absolute value
  * @param migrateFromChatId      Integer	Optional The supergroup has been migrated from a group with the specified identifier, not exceeding 1e13 by absolute value
  * @param pinnedMessage          Message	Optional Specified message was pinned. Note that the Message object in this field will not contain further reply_to_message fields even if it is itself a reply.
  */
case class  Message(
               messageId             : Long,
               from                  : Option[User] = None, // This field is absent when returned from (SendMessage to a channel)
               date                  : Int,
               chat                  : Chat,
               forwardFrom           : Option[User] = None,
               forwardFromChat	     : Option[Chat] = None,
               forwardDate           : Option[Int] = None,
               replyToMessage        : Option[Message] = None,
               editDate              : Option[Int] = None,
               text                  : Option[String] = None,
               entities              : Option[Seq[MessageEntity]] = None,
               audio                 : Option[Audio] = None,
               document              : Option[Document] = None,
               photo                 : Option[Array[PhotoSize]] = None,
               sticker               : Option[Sticker] = None,
               video                 : Option[Video] = None,
               voice                 : Option[Voice] = None,
               caption               : Option[String] = None,
               contact               : Option[Contact] = None,
               location              : Option[Location] = None,
               venue                 : Option[Venue] = None,
               newChatMember         : Option[User] = None,
               leftChatMember        : Option[User] = None,
               newChatTitle          : Option[String] = None,
               newChatPhoto          : Option[Array[PhotoSize]] = None,
               deleteChatPhoto       : Option[Boolean] = None,
               groupChatCreated      : Option[Boolean] = None,
               supergroupChatCreated : Option[Boolean] = None,
               channelChatCreated	   : Option[Boolean] = None,
               migrateToChatId	     : Option[Long] = None,
               migrateFromChatId	   : Option[Long] = None,
               pinnedMessage         : Option[Message] = None
             ) {
  def sender = chat.id
}

