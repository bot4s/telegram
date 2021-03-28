package com.bot4s.telegram.methods

/**
  * Represent a type of poll
  */
object PollType extends Enumeration {
  type PollType = Value
  val regular = Value("regular")
  val quiz = Value("quiz")
}
