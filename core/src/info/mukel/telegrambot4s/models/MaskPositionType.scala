package info.mukel.telegrambot4s.models

/**
  * The part of the face relative to which the mask should be placed.
  * One of "forehead", "eyes", "mouth", or "chin".
  */
object MaskPositionType extends Enumeration {
  type MaskPositionType = Value
  val Forehead, Eyes, Mouth, Chin = Value
}
