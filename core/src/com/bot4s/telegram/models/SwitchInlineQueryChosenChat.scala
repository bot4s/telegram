package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object represents an inline button that switches the current user to inline mode in a chosen chat, with an optional default inline query.
 *
 * @param query The default inline query to be inserted in the input field. If left empty, only the bot's username will be inserted
 * @param allowUserChats True, if private chats with users can be chosen
 * @param allowBotChats True, if private chats with bots can be chosen
 * @param allowGroupChats True, if group and supergroup chats can be chosen
 * @param allowChannelChats True, if channel chats can be chosen
 */
final case class SwitchInlineQueryChosenChat(
  query: Option[String] = None,
  allowUserChats: Option[Boolean] = None,
  allowBotChats: Option[Boolean] = None,
  allowGroupChats: Option[Boolean] = None,
  allowChannelChats: Option[Boolean] = None
)

object SwitchInlineQueryChosenChat {
  implicit val circeDecoder: Decoder[SwitchInlineQueryChosenChat] = deriveDecoder[SwitchInlineQueryChosenChat]
}
