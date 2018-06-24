package info.mukel.telegrambot4s.models

object MemberStatus extends Enumeration {
  type MemberStatus = Value
  val Creator, Administrator, Member, Left, Kicked = Value
}
