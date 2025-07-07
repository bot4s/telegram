package com.bot4s.telegram.models

import com.bot4s.telegram.models.ChatType.ChatType
import io.circe.{ Decoder, Encoder }
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.Configuration

/**
 * This object represents a chat.
 *
 * @param id             Integer Unique identifier for this chat, not exceeding 1e13 by absolute value
 * @param type           String Type of chat, can be either "private", "group", "supergroup" or "channel"
 * @param title          String Optional Title, for channels and group chats
 * @param username       String Optional Username, for private chats and channels if available
 * @param firstName      String Optional First name of the other party in a private chat
 * @param lastName       String Optional Last name of the other party in a private chat
 * @param photo          ChatPhoto	Optional. Chat photo. Returned only in getChat.
 * @param bio            String	Optional. Optional. Bio of the other party in a private chat. Returned only in getChat.
 * @param description    String Optional. Description, for groups, supergroups and channel chats. Returned only in getChat.
 * @param inviteLink	    String Optional. Optional. Chat invite link, for groups, supergroups and channel chats.
 *                       Each administrator in a chat generates their own invite links, so the bot must first generate the link using exportChatInviteLink.
 *                       Returned only in getChat.
 *
 * @param pinnedMessage                       Message Optional. Pinned message, for supergroups. Returned only in getChat.
 * @param permissions                         ChatPermissions Optional. Default chat member permissions, for groups and supergroups. Returned only in getChat.
 * @param slowModeDelay                       Integer Optional. For supergroups, the minimum allowed delay between consecutive messages sent by each unpriviledged user; in seconds. Returned only in getChat.
 * @param messageAutoDeleteTime               Integer Optional. The time after which all messages sent to the chat will be automatically deleted; in seconds. Returned only in getChat.
 * @param stickerSetName                      String Optional. For supergroups, name of group sticker set. Returned only in getChat.
 * @param canSetStickerSet                    Boolean Optional. True, if the bot can change the group sticker set. Returned only in getChat.
 * @param linkedChatId                        Long Optinal. Unique identifier for the linked chat, i.e. the discussion group identifier for a channel and vice versa; for supergroups and channel chats.
 *                                            This identifier may be greater than 32 bits and some programming languages may have difficulty/silent defects in interpreting it.
 *                                            But it is smaller than 52 bits, so a signed 64 bit integer or double-precision float type are safe for storing this identifier. Returned only in getChat.
 * @param location                            Optional ChatLocation. For supergroups, the location to which the supergroup is connected. Returned only in getChat.
 * @param hasPrivateForwards                  Boolean Optional. True, if privacy settings of the other party in the private chat allows to use tg://user?id=<user_id> links only in chats with the user.
 *                                              Returned only in getChat.
 * @param hasProtectedContent                 Boolean Optional. True, if messages from the chat can't be forwarded to other chats. Returned only in getChat.
 * @param joinToSendMessages                  Boolean Optional. True, if users need to join the supergroup before they can send messages. Returned only in getChat.
 * @param joinByRequest                       Boolean Optional. True, if all users directly joining the supergroup need to be approved by supergroup administrators. Returned only in getChat.
 * @param hasRestrictedVoiceAndVideoMessages 	Boolean Optional. True, if the privacy settings of the other party restrict sending voice and video note messages in the private chat. Returned only in getChat.
 * @param isForum                             Boolean Optional. True, if the supergroup chat is a forum (has topics enabled)
 * @param activeUsernames                     Array of string Optional. If non-empty, the list of all active chat usernames; for private chats, supergroups and channels. Returned only in getChat.
 * @param emojiStatusCustomEmojiId            String Optional. Custom emoji identifier of emoji status of the other party in a private chat. Returned only in getChat.
 * @param emojiStatusExpirationDate           Integer Optional. Expiration date of the emoji status of the other party in a private chat in Unix time, if any. Returned only in getChat.
 * @param hasHiddenMembers                    Boolean Optional. True, if non-administrators can only get the list of bots and administrators in the chat. Returned only in getChat
 * @param hasAggressiveAntiSpamEnabled        Boolean Optional. True, if the aggressive anti-spam checks are enabled in the supergroup. The field is only available to chat administrators. Returned only in getChat
 */
case class Chat(
  id: Long,
  `type`: ChatType,
  title: Option[String] = None,
  username: Option[String] = None,
  firstName: Option[String] = None,
  lastName: Option[String] = None,
  photo: Option[ChatPhoto] = None,
  bio: Option[String] = None,
  description: Option[String] = None,
  inviteLink: Option[String] = None,
  pinnedMessage: Option[Message] = None,
  permissions: Option[ChatPermissions] = None,
  slowModeDelay: Option[Int] = None,
  messageAutoDeleteTime: Option[Int] = None,
  stickerSetName: Option[String] = None,
  canSetStickerSet: Option[Boolean] = None,
  linkedChatId: Option[Long] = None,
  location: Option[ChatLocation] = None,
  hasPrivateForwards: Option[Boolean] = None,
  hasProtectedContent: Option[Boolean] = None,
  joinToSendMessages: Option[Boolean] = None,
  joinByRequest: Option[Boolean] = None,
  hasRestrictedVoiceAndVideoMessages: Option[Boolean] = None,
  isForum: Option[Boolean] = None,
  activeUsernames: Option[List[String]] = None,
  emojiStatusCustomEmojiId: Option[String] = None,
  emojiStatusExpirationDate: Option[Long] = None,
  hasHiddenMembers: Option[Boolean] = None,
  hasAggressiveAntiSpamEnabled: Option[Boolean] = None
) {
  val chatId = ChatId(id)
}

object Chat {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[Chat] = deriveDecoder[Chat]
  implicit val circeEncoder: Encoder[Chat] = deriveConfiguredEncoder[Chat]
}
