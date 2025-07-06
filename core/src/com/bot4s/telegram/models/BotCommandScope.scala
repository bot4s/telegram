package com.bot4s.telegram.models

import io.circe.{Decoder, Encoder}
import com.bot4s.telegram.marshalling._

object BotCommandScope extends Enumeration {
  type BotCommandScope = Value
  val Default, AllPrivateChats, AllGroupChats, AllChatAdministrators, Chat, ChatAdministrators, ChatMember = Value

  implicit val circeDecoder: Decoder[BotCommandScope] =
    Decoder[String].map(s => BotCommandScope.withName(pascalize(s)))
  implicit val circeEncoder: Encoder[BotCommandScope] =
    Encoder[String].contramap(e => CaseConversions.snakenize(e.toString))
}
