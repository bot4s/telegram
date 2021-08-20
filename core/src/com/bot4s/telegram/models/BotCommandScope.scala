package com.bot4s.telegram.models

object BotCommandScope extends Enumeration {
  type BotCommandScope = Value
  val Default, AllPrivateChats, AllGroupChats, AllChatAdministrators, Chat, ChatAdministrators, ChatMember = Value
}
