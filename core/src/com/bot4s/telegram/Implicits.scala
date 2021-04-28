package com.bot4s.telegram

/**
  * Useful implicits to reduce boilerplate.
  *
  * Implicit Option conversion may have unexpected effects, use wisely.
  */
object Implicits {

  implicit def toOption[T](v: T): Option[T] = Option(v)

  implicit class MarkdownString(val s: String) extends AnyVal {
    def bold = s"*$s*"

    def italic = s"_${s}_"

    def urlWithAlt(alt: String) = s"[$alt]($s)"

    def altWithUrl(url: String) = s"[$s]($url)"

    def mention(userId: Long) = s"[$s](tg://user?id=$userId)"

    def inlineCode = s"`$s`"

    def blockCode(language: String = "text") = s"```$language\n$s\n```"

    // Escape Markdown
    def mdEscape: String = {
      s.replaceAll("([" + "*_`[".replaceAll("(.)", "\\\\$1") + "])", "\\\\$1")
    }
  }

}
