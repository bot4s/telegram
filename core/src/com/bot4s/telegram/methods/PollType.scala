package com.bot4s.telegram.methods

import io.circe.{ Decoder, Encoder }
import com.bot4s.telegram.marshalling._

/**
 * Represent a type of poll
 */
object PollType extends Enumeration {
  type PollType = Value
  val regular = Value("regular")
  val quiz    = Value("quiz")

  implicit val circeDecoder: Decoder[PollType] =
    Decoder[String].map(s => PollType.withName(pascalize(s)))
  implicit val circeEncoder: Encoder[PollType] =
    Encoder[String].contramap[PollType](e => CaseConversions.snakenize(e.toString))
}
