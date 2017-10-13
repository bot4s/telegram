package info.mukel.telegrambot4s

import scala.language.implicitConversions

/**
  * Useful/scary implicits to reduce boilerplate.
  *
  * Note that implicit Option conversion can have unexpected side effects,
  * use wisely; at your own risk.
  */
object Implicits {

  implicit def toOption[T](v: T) : Option[T] = Option(v)

  implicit class MarkdownString(val s: String) extends AnyVal {
    def bold = s"*$s*"
    def italic = s"_${s}_"
    def urlWithAlt(alt: String) = s"[$alt]($s)"
    def altWithUrl(url: String) = s"[$s]($url)"
    def mention(userId: Int) = s"[$s]($$tg://user?id=$userId)"
    def inlineCode = s"`$s`"
    def blockCode(language: String = "text") = s"```$language\n$s\n```"
    // Markdown escape
    def md = s.replaceAll("([" + "*_`[".replaceAll("(.)", "\\\\$1") + "])", "\\\\$1")
  }
}
