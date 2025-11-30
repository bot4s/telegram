package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.syntax.EncoderOps
import io.circe.HCursor
import io.circe.Json

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
 * @param mediaGroupId                Optional. The unique identifier of a media message group this message belongs to
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
  hasMediaSpoiler: Option[Boolean] = None,
  mediaGroupId: Option[String] = None
) {

  def source: Long = chat.id
}

object Message {
  // Implementing Encoder / Decoder manually
  // Until https://github.com/scala/scala3/issues/22688 is resolved
  implicit val circeDecoder: Decoder[Message] = new Decoder[Message] {
    final def apply(c: HCursor): Decoder.Result[Message] =
      for {
        messageId             <- c.downField("messageId").as[Int]
        from                  <- c.getOrElse[Option[User]]("from")(None)
        date                  <- c.downField("date").as[Int]
        chat                  <- c.downField("chat").as[Chat]
        forwardFrom           <- c.getOrElse[Option[User]]("forwardFrom")(None)
        forwardFromChat       <- c.getOrElse[Option[Chat]]("forwardFromChat")(None)
        forwardFromMessageId  <- c.getOrElse[Option[Int]]("forwardFromMessageId")(None)
        forwardSignature      <- c.getOrElse[Option[String]]("forwardSignature")(None)
        forwardSenderName     <- c.getOrElse[Option[String]]("forwardSenderName")(None)
        forwardDate           <- c.getOrElse[Option[Int]]("forwardDate")(None)
        replyToMessage        <- c.getOrElse[Option[Message]]("replyToMessage")(None)
        editDate              <- c.getOrElse[Option[Int]]("editDate")(None)
        authorSignature       <- c.getOrElse[Option[String]]("authorSignature")(None)
        text                  <- c.getOrElse[Option[String]]("text")(None)
        entities              <- c.getOrElse[Option[Seq[MessageEntity]]]("entities")(None)
        captionEntities       <- c.getOrElse[Option[Array[MessageEntity]]]("captionEntities")(None)
        audio                 <- c.getOrElse[Option[Audio]]("audio")(None)
        document              <- c.getOrElse[Option[Document]]("document")(None)
        animation             <- c.getOrElse[Option[Animation]]("animation")(None)
        game                  <- c.getOrElse[Option[Game]]("game")(None)
        photo                 <- c.getOrElse[Option[Seq[PhotoSize]]]("photo")(None)
        sticker               <- c.getOrElse[Option[Sticker]]("sticker")(None)
        story                 <- c.getOrElse[Option[Story.type]]("story")(None)
        video                 <- c.getOrElse[Option[Video]]("video")(None)
        voice                 <- c.getOrElse[Option[Voice]]("voice")(None)
        videoNote             <- c.getOrElse[Option[VideoNote]]("videoNote")(None)
        newChatMembers        <- c.getOrElse[Option[Array[User]]]("newChatMembers")(None)
        caption               <- c.getOrElse[Option[String]]("caption")(None)
        contact               <- c.getOrElse[Option[Contact]]("contact")(None)
        location              <- c.getOrElse[Option[Location]]("location")(None)
        venue                 <- c.getOrElse[Option[Venue]]("venue")(None)
        poll                  <- c.getOrElse[Option[Poll]]("poll")(None)
        leftChatMember        <- c.getOrElse[Option[User]]("leftChatMember")(None)
        newChatTitle          <- c.getOrElse[Option[String]]("newChatTitle")(None)
        newChatPhoto          <- c.getOrElse[Option[Seq[PhotoSize]]]("newChatPhoto")(None)
        deleteChatPhoto       <- c.getOrElse[Option[Boolean]]("deleteChatPhoto")(None)
        groupChatCreated      <- c.getOrElse[Option[Boolean]]("groupChatCreated")(None)
        supergroupChatCreated <- c.getOrElse[Option[Boolean]]("supergroupChatCreated")(None)
        channelChatCreated    <- c.getOrElse[Option[Boolean]]("channelChatCreated")(None)
        migrateToChatId       <- c.getOrElse[Option[Long]]("migrateToChatId")(None)
        migrateFromChatId     <- c.getOrElse[Option[Long]]("migrateFromChatId")(None)
        pinnedMessage         <- c.getOrElse[Option[Message]]("pinnedMessage")(None)
        invoice               <- c.getOrElse[Option[Invoice]]("invoice")(None)
        successfulPayment     <- c.getOrElse[Option[SuccessfulPayment]]("successfulPayment")(None)
        userShared            <- c.getOrElse[Option[UserShared]]("userShared")(None)
        chatShared            <- c.getOrElse[Option[ChatShared]]("chatShared")(None)
        connectedWebsite      <- c.getOrElse[Option[String]]("connectedWebsite")(None)
        replyMarkup           <- c.getOrElse[Option[InlineKeyboardMarkup]]("replyMarkup")(None)
        hasProtectedContent   <- c.getOrElse[Option[Boolean]]("hasProtectedContent")(None)
        isAutomaticForward    <- c.getOrElse[Option[Boolean]]("isAutomaticForward")(None)
        senderChat            <- c.getOrElse[Option[Chat]]("senderChat")(None)
        webAppData            <- c.getOrElse[Option[WebAppData]]("webAppData")(None)
        videoChatScheduled    <- c.getOrElse[Option[VideoChatScheduled]]("videoChatScheduled")(None)
        videoChatStarted      <- c.getOrElse[Option[VideoChatStarted.type]]("videoChatStarted")(None)
        videoChatEnded        <- c.getOrElse[Option[VideoChatEnded]]("videoChatEnded")(None)
        videoChatParticipantsInvited <-
          c.getOrElse[Option[VideoChatParticipantsInvited]]("videoChatParticipantsInvited")(None)
        messageThreadId         <- c.getOrElse[Option[Int]]("messageThreadId")(None)
        isTopicMessage          <- c.getOrElse[Option[Boolean]]("isTopicMessage")(None)
        forumTopicCreated       <- c.getOrElse[Option[ForumTopicCreated]]("forumTopicCreated")(None)
        forumTopicEdited        <- c.getOrElse[Option[ForumTopicEdited]]("forumTopicEdited")(None)
        forumTopicClosed        <- c.getOrElse[Option[ForumTopicClosed.type]]("forumTopicClosed")(None)
        forumTopicReopened      <- c.getOrElse[Option[ForumTopicReopened.type]]("forumTopicReopened")(None)
        generalForumTopicHidden <- c.getOrElse[Option[GeneralForumTopicHidden.type]]("generalForumTopicHidden")(None)
        generalForumTopicunHidden <-
          c.getOrElse[Option[GeneralForumTopicUnhidden.type]]("generalForumTopicunHidden")(None)
        writeAccessAllowed <- c.getOrElse[Option[WriteAccessAllowed]]("writeAccessAllowed")(None)
        hasMediaSpoiler    <- c.getOrElse[Option[Boolean]]("hasMediaSpoiler")(None)
        mediaGroupId       <- c.getOrElse[Option[String]]("mediaGroupId")(None)
      } yield {
        Message(
          messageId,
          from,
          date,
          chat,
          forwardFrom,
          forwardFromChat,
          forwardFromMessageId,
          forwardSignature,
          forwardSenderName,
          forwardDate,
          replyToMessage,
          editDate,
          authorSignature,
          text,
          entities,
          captionEntities,
          audio,
          document,
          animation,
          game,
          photo,
          sticker,
          story,
          video,
          voice,
          videoNote,
          newChatMembers,
          caption,
          contact,
          location,
          venue,
          poll,
          leftChatMember,
          newChatTitle,
          newChatPhoto,
          deleteChatPhoto,
          groupChatCreated,
          supergroupChatCreated,
          channelChatCreated,
          migrateToChatId,
          migrateFromChatId,
          pinnedMessage,
          invoice,
          successfulPayment,
          userShared,
          chatShared,
          connectedWebsite,
          replyMarkup,
          hasProtectedContent,
          isAutomaticForward,
          senderChat,
          webAppData,
          videoChatScheduled,
          videoChatStarted,
          videoChatEnded,
          videoChatParticipantsInvited,
          messageThreadId,
          isTopicMessage,
          forumTopicCreated,
          forumTopicEdited,
          forumTopicClosed,
          forumTopicReopened,
          generalForumTopicHidden,
          generalForumTopicunHidden,
          writeAccessAllowed,
          hasMediaSpoiler,
          mediaGroupId
        )
      }
  }
  implicit val circeEncoder: Encoder[Message] = Encoder.instance[Message] { v =>
    Json.obj(
      "message_id"                      -> v.messageId.asJson,
      "from"                            -> v.from.asJson,
      "date"                            -> v.date.asJson,
      "chat"                            -> v.chat.asJson,
      "forward_from"                    -> v.forwardFrom.asJson,
      "forward_from_chat"               -> v.forwardFromChat.asJson,
      "forward_from_message_id"         -> v.forwardFromMessageId.asJson,
      "forward_signature"               -> v.forwardSignature.asJson,
      "forward_sender_name"             -> v.forwardSenderName.asJson,
      "forward_date"                    -> v.forwardDate.asJson,
      "reply_to_message"                -> v.replyToMessage.asJson,
      "edit_date"                       -> v.editDate.asJson,
      "author_signature"                -> v.authorSignature.asJson,
      "text"                            -> v.text.asJson,
      "entities"                        -> v.entities.asJson,
      "caption_entities"                -> v.captionEntities.asJson,
      "audio"                           -> v.audio.asJson,
      "document"                        -> v.document.asJson,
      "animation"                       -> v.animation.asJson,
      "game"                            -> v.game.asJson,
      "photo"                           -> v.photo.asJson,
      "sticker"                         -> v.sticker.asJson,
      "story"                           -> v.story.asJson,
      "video"                           -> v.video.asJson,
      "voice"                           -> v.voice.asJson,
      "video_note"                      -> v.videoNote.asJson,
      "new_chat_members"                -> v.newChatMembers.asJson,
      "caption"                         -> v.caption.asJson,
      "contact"                         -> v.contact.asJson,
      "location"                        -> v.location.asJson,
      "venue"                           -> v.venue.asJson,
      "poll"                            -> v.poll.asJson,
      "left_chat_member"                -> v.leftChatMember.asJson,
      "new_chat_title"                  -> v.newChatTitle.asJson,
      "new_chat_photo"                  -> v.newChatPhoto.asJson,
      "delete_chat_photo"               -> v.deleteChatPhoto.asJson,
      "group_chat_created"              -> v.groupChatCreated.asJson,
      "supergroup_chat_created"         -> v.supergroupChatCreated.asJson,
      "channel_chat_created"            -> v.channelChatCreated.asJson,
      "migrate_to_chat_id"              -> v.migrateToChatId.asJson,
      "migrate_from_chat_id"            -> v.migrateFromChatId.asJson,
      "pinned_message"                  -> v.pinnedMessage.asJson,
      "invoice"                         -> v.invoice.asJson,
      "successful_payment"              -> v.successfulPayment.asJson,
      "user_shared"                     -> v.userShared.asJson,
      "chat_shared"                     -> v.chatShared.asJson,
      "connected_website"               -> v.connectedWebsite.asJson,
      "reply_markup"                    -> v.replyMarkup.asJson,
      "has_protected_content"           -> v.hasProtectedContent.asJson,
      "is_automatic_forward"            -> v.isAutomaticForward.asJson,
      "sender_chat"                     -> v.senderChat.asJson,
      "web_app_data"                    -> v.webAppData.asJson,
      "video_chat_scheduled"            -> v.videoChatScheduled.asJson,
      "video_chat_started"              -> v.videoChatStarted.asJson,
      "video_chat_ended"                -> v.videoChatEnded.asJson,
      "video_chat_participants_invited" -> v.videoChatParticipantsInvited.asJson,
      "message_thread_id"               -> v.messageThreadId.asJson,
      "is_topic_message"                -> v.isTopicMessage.asJson,
      "forum_topic_created"             -> v.forumTopicCreated.asJson,
      "forum_topic_edited"              -> v.forumTopicEdited.asJson,
      "forum_topic_closed"              -> v.forumTopicClosed.asJson,
      "forum_topic_reopened"            -> v.forumTopicReopened.asJson,
      "general_forum_topic_hidden"      -> v.generalForumTopicHidden.asJson,
      "general_forum_topicun_hidden"    -> v.generalForumTopicunHidden.asJson,
      "write_access_allowed"            -> v.writeAccessAllowed.asJson,
      "has_media_spoiler"               -> v.hasMediaSpoiler.asJson,
      "media_group_id"                  -> v.mediaGroupId.asJson
    )
  }
}
