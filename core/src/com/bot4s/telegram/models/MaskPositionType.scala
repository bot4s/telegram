package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import com.bot4s.telegram.marshalling._

/**
 * The part of the face relative to which the mask should be placed.
 * One of "forehead", "eyes", "mouth", or "chin".
 */
object MaskPositionType extends Enumeration {
  type MaskPositionType = Value
  val Forehead, Eyes, Mouth, Chin = Value

  implicit val circeDecoder: Decoder[MaskPositionType] =
    Decoder[String].map(s => MaskPositionType.withName(pascalize(s)))
  implicit val circeEncoder: Encoder[MaskPositionType] =
    Encoder[String].contramap[MaskPositionType](e => CaseConversions.snakenize(e.toString))
}
