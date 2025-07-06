package com.bot4s.telegram.methods

import com.bot4s.telegram.models.Message
import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to forward messages of any kind. On success, the sent Message is returned.
 *
 * @param chatId               Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param fromChatId           Integer or String Unique identifier for the chat where the original message was sent (or channel username in the format @channelusername)
 * @param disableNotification  Boolean Optional Sends the message silently. iOS users will not receive a notification, Android users will receive a notification with no sound.
 * @param protectContent       Boolean Optional Protects the contents of the sent message from forwarding and saving
 * @param messageId            Integer Unique message identifier
 * @param messageThreadId      Optional Integer. Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
 */
case class ForwardMessage(
  chatId: ChatId,
  fromChatId: ChatId,
  disableNotification: Option[Boolean] = None,
  protectContent: Option[Boolean] = None,
  messageId: Int,
  messageThreadId: Option[Int] = None
) extends JsonRequest {
  type Response = Message
}

object ForwardMessage {
  implicit val customConfig: Configuration           = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[ForwardMessage] = deriveConfiguredEncoder
}
