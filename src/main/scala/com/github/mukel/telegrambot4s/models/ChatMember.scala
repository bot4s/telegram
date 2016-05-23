package com.github.mukel.telegrambot4s.models

/** This object contains information about one member of the chat.
  *
  * @param user	User	Information about the user
  * @param status	String	The member's status in the chat. Can be “creator”, “administrator”, “member”, “left” or “kicked”
  */
case class ChatMember(
                     user   : User,
                     status : String
                     )
