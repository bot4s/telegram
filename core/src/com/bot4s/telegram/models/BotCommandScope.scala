package com.bot4s.telegram.models

enum BotCommandScope:
  case Default
  case AllPrivateChats
  case AllGroupChats
  case AllChatAdministrators
  case Chat
  case ChatAdministrators
  case ChatMember
