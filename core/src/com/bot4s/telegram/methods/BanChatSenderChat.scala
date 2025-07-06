package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to ban a channel chat in a supergroup or a channel.
 * Until the chat is unbanned, the owner of the banned chat won't be able to send messages on behalf of any of their channels.
 * The bot must be an administrator in the supergroup or channel for this to work and must have the appropriate administrator rights.
 *  Returns True on success.
 *
 * @param chatId        Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 *  @param senderChatId  Unique identifier of the target sender chat
 */
case class BanChatSenderChat(
  chatId: ChatId,
  sendChatId: Long
) extends JsonRequest[Boolean]

object BanChatSenderChat {
  implicit val customConfig: Configuration              = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[BanChatSenderChat] = deriveConfiguredEncoder
}
