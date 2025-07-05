package com.bot4s.telegram.models

import io.circe.Decoder
import com.bot4s.telegram.marshalling._

object BotCommandScope extends Enumeration {
  type BotCommandScope = Value
  val Default, AllPrivateChats, AllGroupChats, AllChatAdministrators, Chat, ChatAdministrators, ChatMember = Value

  implicit val circeDecoder: Decoder[BotCommandScope] =
    Decoder[String].map(s => BotCommandScope.withName(pascalize(s)))
}
