package com.bot4s.telegram.models

import com.bot4s.telegram.models.MaskPositionType.MaskPositionType
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * This object describes the position on faces where a mask should be placed by default.
 *
 * @param point   String The part of the face relative to which the mask should be placed.
 *                One of "forehead", "eyes", "mouth", or "chin".
 * @param xShift  Float number Shift by X-axis measured in widths of the mask scaled to the face size, from left to right.
 *                For example, choosing -1.0 will place mask just to the left of the default mask position.
 * @param yShift  Float number Shift by Y-axis measured in heights of the mask scaled to the face size, from top to bottom.
 *                For example, 1.0 will place the mask just below the default mask position.
 * @param zoom    Float number Mask scaling coefficient. For example, 2.0 means double size.
 */
case class MaskPosition(point: MaskPositionType, xShift: Double, yShift: Double, zoom: Double)

object MaskPosition {
  implicit val circeDecoder: Decoder[MaskPosition] = deriveDecoder[MaskPosition]
}
