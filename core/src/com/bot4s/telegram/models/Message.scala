package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents a message.
 *
 * @param messageId                   Unique message identifier
 * @param from                        Sender
 * @param date                        Date the message was sent in Unix time
 * @param chat                        User or GroupChat  Conversation the message belongs to - user in case of a
 *                                    private message, GroupChat in case of a group
 * @param forwardFrom                 Optional For forwarded messages, sender of the original message
 * @param forwardFromChat             Optional For messages forwarded from a channel, information about the original channel
 * @param forwardFromMessageId        Integer Optional. For forwarded channel posts, identifier of the original message in the channel
 * @param forwardSignature            String Optional. For messages forwarded from channels, signature of the post author if present
 * @param forwardSenderName           String Optional. Sender's name for messages forwarded from users who disallow adding a link to their account in forwarded messages
 * @param forwardDate                 Optional For forwarded messages, date the original message was sent in Unix time
 * @param replyToMessage              Optional For replies, the original message. Note that the Message object in this field
 *                                    will not contain further replyToMessage fields even if it itself is a reply.
 * @param editDate                    Optional. Date the message was last edited in Unix time
 * @param authorSignature             String Optional. Signature of the post author for messages in channels
 * @param text                        Optional For text messages, the actual UTF-8 text of the message
 * @param entities                    Array of MessageEntity Optional For text messages, special entities like usernames,
 *                                    URLs, bot commands, etc. that appear in the text
 * @param captionEntities             Array of MessageEntity Optional. For messages with a caption, special entities
 *                                    like usernames, URLs, bot commands, etc. that appear in the caption
 * @param audio                       Optional Message is an audio file, information about the file
 * @param document                    Optional Message is a general file, information about the file
 * @param animation                   Optional. Message is an animation, information about the animation.
 *                                    For backward compatibility, when this field is set, the document field will also be set
 * @param game                        Game Optional. Message is a game, information about the game.
 *                                    [[https://core.telegram.org/bots/api#games More about games »]]
 * @param photo                       Optional Message is a photo, available sizes of the photo
 * @param sticker                     Optional Message is a sticker, information about the sticker
 * @param story                       Optional Message is a forwarded story
 * @param video                       Optional Message is a video, information about the video
 * @param voice                       Optional Message is a voice message, information about the file
 * @param videoNote                   Optional Message is a video note, information about the video message
 * @param newChatMembers              Array of User Optional. New members that were added to the group or supergroup and
 *                                    information about them (the bot itself may be one of these members)
 * @param caption                     Optional Caption for the photo or video
 * @param contact                     Optional Message is a shared contact, information about the contact
 * @param location                    Optional Message is a shared location, information about the location
 * @param venue                       Venue Optional Message is a venue, information about the venue
 * @param poll                        Poll Optional. Message is a native poll, information about the poll
 * @param leftChatMember              Optional A member was removed from the group, information about them (this member may be bot itself)
 * @param newChatTitle                Optional A group title was changed to this value
 * @param newChatPhoto                Optional A group photo was change to this value
 * @param deleteChatPhoto             Optional Informs that the group photo was deleted
 * @param groupChatCreated            Optional Informs that the group has been created
 * @param supergroupChatCreated       True Optional Service message: the supergroup has been created
 * @param channelChatCreated          True Optional Service message: the channel has been created
 * @param migrateToChatId             Integer Optional The group has been migrated to a supergroup with the specified
 *                                    identifier, not exceeding 1e13 by absolute value
 * @param migrateFromChatId           Integer Optional The supergroup has been migrated from a group with the specified
 *                                    identifier, not exceeding 1e13 by absolute value
 * @param pinnedMessage               Message Optional Specified message was pinned. Note that the Message object in this
 *                                    field will not contain further reply_to_message fields even if it is itself a reply.
 * @param invoice                     Invoice Optional. Message is an invoice for a payment, information about the invoice.
 *                                    [[https://core.telegram.org/bots/api#payments More about payments »]]
 * @param successfulPayment           Optional. Message is a service message about a successful payment, information about the payment.
 * @param connectedWebsite            String Optional. The domain name of the website on which the user has logged in.
 *                                    [[https://core.telegram.org/widgets/login More about Telegram Login »]]
 * @param replyMarkup                 InlineKeyboardMarkup Optional. Inline keyboard attached to the message.
 *                                    login_url buttons are represented as ordinary url buttons.
 * @param hasProtectedContent         Optional. True, if messages from the chat can't be forwarded to other chats. Returned only in getChat.
 * @param isAutomaticForward          Optional. True, if the message is a channel post that was automatically forwarded to the connected discussion group
 * @param senderChat                  Optional. Sender of the message; empty for messages sent to channels. For backward compatibility, the field contains a fake sender user in non-channel chats, if the message was sent on behalf of a chat.
 * @param webAppData 	                WebAppData 	Optional. Service message: data sent by a Web App
 * @param messageThreadId             Optional. Unique identifier of a message thread to which the message belongs; for supergroups only
 * @param isTopicMessage              Optional. True, if the message is sent to a forum topic
 * @param forumTopicCreated 	        ForumTopicCreated Optional. Service message: forum topic created
 * @param forumTopicEdited 	          ForumTopicEdited Optional. Service message: forum topic edited
 * @param forumTopicClosed 	          ForumTopicClosed Optional. Service message: forum topic closed
 * @param forumTopicReopened 	        ForumTopicReopened Optional. Service message: forum topic reopened
 * @param generalForumTopicHidden     GeneralForumTopicHidden. Optional. Service message: the 'General' forum topic hidden
 * @param generalForumTopicunHidden   GeneralForumTopicUnhidden. Optinal. Service message: the 'General' forum topic unhidden
 * @param writeAccessAllowed          WrieAccessAllowed. Optional. Service message: the user allowed the bot added to the attachment menu to write messages
 * @param hasMediaSpoiler             Optional. True, if the message media is covered by a spoiler animation
 */
case class Message(
  messageId: Int,
  from: Option[User] = None,
  date: Int,
  chat: Chat,
  forwardFrom: Option[User] = None,
  forwardFromChat: Option[Chat] = None,
  forwardFromMessageId: Option[Int] = None,
  forwardSignature: Option[String] = None,
  forwardSenderName: Option[String] = None,
  forwardDate: Option[Int] = None,
  replyToMessage: Option[Message] = None,
  editDate: Option[Int] = None,
  authorSignature: Option[String] = None,
  text: Option[String] = None,
  entities: Option[Seq[MessageEntity]] = None,
  captionEntities: Option[Array[MessageEntity]] = None,
  audio: Option[Audio] = None,
  document: Option[Document] = None,
  animation: Option[Animation] = None,
  game: Option[Game] = None,
  photo: Option[Seq[PhotoSize]] = None,
  sticker: Option[Sticker] = None,
  story: Option[Story.type] = None,
  video: Option[Video] = None,
  voice: Option[Voice] = None,
  videoNote: Option[VideoNote] = None,
  newChatMembers: Option[Array[User]] = None,
  caption: Option[String] = None,
  contact: Option[Contact] = None,
  location: Option[Location] = None,
  venue: Option[Venue] = None,
  poll: Option[Poll] = None,
  leftChatMember: Option[User] = None,
  newChatTitle: Option[String] = None,
  newChatPhoto: Option[Seq[PhotoSize]] = None,
  deleteChatPhoto: Option[Boolean] = None,
  groupChatCreated: Option[Boolean] = None,
  supergroupChatCreated: Option[Boolean] = None,
  channelChatCreated: Option[Boolean] = None,
  migrateToChatId: Option[Long] = None,
  migrateFromChatId: Option[Long] = None,
  pinnedMessage: Option[Message] = None,
  invoice: Option[Invoice] = None,
  successfulPayment: Option[SuccessfulPayment] = None,
  userShared: Option[UserShared] = None,
  chatShared: Option[ChatShared] = None,
  connectedWebsite: Option[String] = None,
  replyMarkup: Option[InlineKeyboardMarkup] = None,
  hasProtectedContent: Option[Boolean] = None,
  isAutomaticForward: Option[Boolean] = None,
  senderChat: Option[Chat] = None,
  webAppData: Option[WebAppData] = None,
  videoChatScheduled: Option[VideoChatScheduled] = None,
  videoChatStarted: Option[VideoChatStarted.type] = None,
  videoChatEnded: Option[VideoChatEnded] = None,
  videoChatParticipantsInvited: Option[VideoChatParticipantsInvited] = None,
  messageThreadId: Option[Int] = None,
  isTopicMessage: Option[Boolean] = None,
  forumTopicCreated: Option[ForumTopicCreated] = None,
  forumTopicEdited: Option[ForumTopicEdited] = None,
  forumTopicClosed: Option[ForumTopicClosed.type] = None,
  forumTopicReopened: Option[ForumTopicReopened.type] = None,
  generalForumTopicHidden: Option[GeneralForumTopicHidden.type] = None,
  generalForumTopicunHidden: Option[GeneralForumTopicUnhidden.type] = None,
  writeAccessAllowed: Option[WriteAccessAllowed] = None,
  hasMediaSpoiler: Option[Boolean] = None
) {

  def source: Long = chat.id
}

object Message {
  implicit val circeDecoder: Decoder[Message] = deriveDecoder[Message]
}
