package info.mukel.telegrambot4s.models


/** Type of chat, can be either "private", "group", "supergroup" or "channel"
  */
object ChatType extends Enumeration {
  type ChatType = Value
  val Private, Group, Supergroup, Channel = Value
}
