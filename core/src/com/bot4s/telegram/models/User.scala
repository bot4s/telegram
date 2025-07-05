package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents a Telegram user or bot.
 *
 * @param id           Unique identifier for this user or bot
 * @param isBot        Boolean True, if this user is a bot
 * @param firstName    User's or bot's first name
 * @param lastName     Optional User's or bot's last name
 * @param username     Optional User's or bot's username
 * @param languageCode String Optional. IETF language tag of the user's language
 * @param isPremium    Boolean Optional. True, if this user is a Telegram premium user
 * @param addedToAttachmentMenu   True Optional. True, if this user added the bot to the attachment menu
 * @param canJoinGroups           Boolean Optional. True, if the bot can be invited to groups. Returned only in getMe.
 * @param canReadAllGroupMessages Boolean Optional. True, if privacy mode is disabled for the bot. Returned only in getMe.
 * @param supportsInlineQueries   Boolean Optional. True, if the bot supports inline queries. Returned only in getMe.
 */
case class User(
  id: Long,
  isBot: Boolean,
  firstName: String,
  lastName: Option[String] = None,
  username: Option[String] = None,
  languageCode: Option[String] = None,
  isPremium: Option[Boolean] = None,
  addedToAttachmentMenu: Option[Boolean] = None,
  canJoinGroups: Option[Boolean] = None,
  canReadAllGroupMessages: Option[Boolean] = None,
  supportsInlineQueries: Option[Boolean] = None
)

object User {
  implicit val circeDecoder: Decoder[User] = deriveDecoder[User]
}
