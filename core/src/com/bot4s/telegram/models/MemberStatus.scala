package com.bot4s.telegram.models

import io.circe.Decoder
import com.bot4s.telegram.marshalling._

object MemberStatus extends Enumeration {
  type MemberStatus = Value
  val Creator, Administrator, Member, Restricted, Left, Kicked = Value

  implicit val circeDecoder: Decoder[MemberStatus] =
    Decoder[String].map(s => MemberStatus.withName(pascalize(s)))
}
