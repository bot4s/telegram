package com.bot4s.telegram.models

/**
 * Type of chat, can be either "private", "group", "supergroup" or "channel"
 */
object ChatType extends Enumeration {
  type ChatType = Value
  val Private, Group, Supergroup, Channel = Value
}
