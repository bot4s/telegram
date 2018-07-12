package com.bot4s.telegram.models

object MemberStatus extends Enumeration {
  type MemberStatus = Value
  val Creator, Administrator, Member, Left, Kicked = Value
}
