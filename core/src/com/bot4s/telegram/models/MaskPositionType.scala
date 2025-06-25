package com.bot4s.telegram.models

/**
 * The part of the face relative to which the mask should be placed.
 * One of "forehead", "eyes", "mouth", or "chin".
 */
enum MaskPositionType:
  case Forehead, Eyes, Mouth, Chin
