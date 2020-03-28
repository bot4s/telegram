package com.bot4s.telegram.marshalling

/** Taken from json4s https://github.com/json4s/json4s */
trait CaseConversions {

  def camelize(word: String): String = {
    if (word.nonEmpty) {
      val w = pascalize(word)
      w.substring(0, 1).toLowerCase + w.substring(1)
    } else {
      word
    }
  }

  def pascalize(word: String): String = {
    val lst = word.split("_").toList
    (lst.headOption
      .map(s ⇒ s.substring(0, 1).toUpperCase + s.substring(1))
      .get ::
      lst.tail.map(s ⇒ s.substring(0, 1).toUpperCase + s.substring(1)))
      .mkString("")
  }

  def snakenize(word: String): String = {
    val spacesPattern = "[-\\s]".r
    val firstPattern = "([A-Z]+)([A-Z][a-z])".r
    val secondPattern = "([a-z\\d])([A-Z])".r
    val replacementPattern = "$1_$2"
    spacesPattern
      .replaceAllIn(
        secondPattern.replaceAllIn(
          firstPattern.replaceAllIn(word, replacementPattern),
          replacementPattern
        ),
        "_"
      )
      .toLowerCase
  }
}

object CaseConversions extends CaseConversions
