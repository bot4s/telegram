package info.mukel.telegrambot4s.models

import info.mukel.telegrambot4s.models.ChatType.ChatType

/** This object represents a chat.
  *
  * @param id             Integer Unique identifier for this chat, not exceeding 1e13 by absolute value
  * @param type           String Type of chat, can be either "private", "group", "supergroup" or "channel"
  * @param title          String Optional Title, for channels and group chats
  * @param username       String Optional Username, for private chats and channels if available
  * @param firstName      String Optional First name of the other party in a private chat
  * @param lastName       String Optional Last name of the other party in a private chat
  * @param allMembersAreAdministrators  Boolean Optional. True if a group has 'All Members Are Admins' enabled.
  * @param photo          ChatPhoto	Optional. Chat photo. Returned only in getChat.
  * @param description    String Optional. Description, for supergroups and channel chats. Returned only in getChat.
  * @param inviteLink	    String Optional. Chat invite link, for supergroups and channel chats. Returned only in getChat.
  * @param pinnedMessage  Message Optional. Pinned message, for supergroups. Returned only in getChat.
  * @param stickerSetName    String Optional. For supergroups, name of group sticker set. Returned only in getChat.
  * @param canSetStickerSet  Boolean Optional. True, if the bot can change the group sticker set. Returned only in getChat.
  */
case class Chat(
                 id            : Long,
                 `type`        : ChatType,
                 title         : Option[String] = None,
                 username      : Option[String] = None,
                 firstName     : Option[String] = None,
                 lastName      : Option[String] = None,
                 allMembersAreAdministrators : Option[Boolean] = None,
                 photo         : Option[ChatPhoto] = None,
                 description   : Option[String] = None,
                 inviteLink    : Option[String] = None,
                 pinnedMessage : Option[Message] = None,
                 stickerSetName: Option[String] = None,
                 canSetStickerSet : Option[Boolean] = None
               )
